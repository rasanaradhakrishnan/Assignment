package com.rasana.assignment.application

import com.rasana.assignment.data.LaunchesService
import com.rasana.assignment.domain.LaunchesRepository
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.*

/**
 * Created by Rasana.
 */
@Module
class AssignmentModule {

    private companion object {
        const val RETROFIT_BASIC = "RETROFIT_BASIC"
        const val GATEWAY_BASIC_HTTP_CLIENT = "gateway_basic_http_client"
        private const val DEFAULT_HTTP_TIMEOUT_SECONDS = 30L
    }

    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))

    @Provides
    @Singleton
    fun provideTLaunchesRepository(@Named(RETROFIT_BASIC) retrofit: Retrofit): LaunchesRepository =
        LaunchesService(retrofit)

    @Provides
    @Singleton
    @Named(RETROFIT_BASIC)
    fun provideOauthRetrofit(
        @Named(GATEWAY_BASIC_HTTP_CLIENT) httpClient: OkHttpClient,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit =
        retrofitBuilder.client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl("https://api.spacexdata.com/")
            .build()

    @Provides
    @Singleton
    @Named(GATEWAY_BASIC_HTTP_CLIENT)
    fun provideGatewayBasicOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor, sslSocketFactory: SSLSocketFactory,
                                  x509TrustManager: X509TrustManager): OkHttpClient =
        createOkHttpBuilder(httpLoggingInterceptor, CertificatePinner.Builder().build(), sslSocketFactory, x509TrustManager)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun createOkHttpBuilder(
        logInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner,
        sslSocketFactory: SSLSocketFactory,
        x509TrustManager: X509TrustManager
    ): OkHttpClient {

        val builder = OkHttpClient().newBuilder()
            .addInterceptor(logInterceptor)
            .connectTimeout(DEFAULT_HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        builder.certificatePinner(certificatePinner)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideX509TrustManager(): X509TrustManager {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(null as KeyStore?)
        return factory.trustManagers[0] as X509TrustManager
    }

    @Provides
    @Singleton
    fun provideSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory =
        SSLSocketFactory.getDefault() as SSLSocketFactory
}