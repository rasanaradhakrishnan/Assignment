package com.rasana.assignment.data

import com.rasana.assignment.domain.LaunchesRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import com.rasana.assignment.domain.AllLaunches as DomainLaunches

/**
 * Created by Rasana.
 */
class LaunchesService constructor(retrofit: Retrofit) : LaunchesRepository {
    private val client = retrofit.create(LaunchesClient::class.java)

    override fun getLaunchesList(sortOrder: String): Single<List<DomainLaunches>> =
        client.getAllLaunches(sortOrder)
                        .cacheAndThreadSafely()
                        .map(::mapDataToDomainLaunches)
                        .onErrorResumeNext { Single.error(it) }

    private fun <T> Single<T>.cacheAndThreadSafely(): Single<T> {
        return cache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    interface LaunchesClient {
        @GET("v3/launches")
        fun getAllLaunches(@Query("sort") sortOrder: String): Single<List<AllLaunches>>
    }
}