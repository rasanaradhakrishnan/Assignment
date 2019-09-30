package com.rasana.assignment.data

import com.rasana.assignment.domain.LaunchesRepository
import com.rasana.assignment.domain.AllLaunches as DomainLaunches
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET

/**
 * Created by Rasana.
 */
class LaunchesService constructor(retrofit: Retrofit) : LaunchesRepository {
    private val client = retrofit.create(LaunchesClient::class.java)

    override fun getLaunchesList(): Single<List<DomainLaunches>> =
                client.getAllLaunches()
                        .cacheAndThreadSafely()
                        .map(::mapDataToDomainLaunches)
                        .onErrorResumeNext { Single.error(it) }

    private fun <T> Single<T>.cacheAndThreadSafely(): Single<T> {
        return cache()
            .subscribeOn(Schedulers.io())
    }

    interface LaunchesClient {
        @GET("v3/launches")
        fun getAllLaunches(): Single<List<AllLaunches>>
    }
}