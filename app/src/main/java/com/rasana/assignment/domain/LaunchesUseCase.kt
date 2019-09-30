package com.rasana.assignment.domain

import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rasana.
 */
class LaunchesUseCase @Inject constructor(private val launchesRepository: LaunchesRepository) {

    fun getAllLaunches(sortOrder: String): Single<List<AllLaunches>> = launchesRepository.getLaunchesList(sortOrder)
}