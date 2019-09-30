package com.rasana.assignment.domain

import io.reactivex.Single

/**
 * Created by Rasana.
 */
interface LaunchesRepository {
    fun getLaunchesList(): Single<List<AllLaunches>>
}