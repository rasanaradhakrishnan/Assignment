package com.rasana.assignment.application

import com.rasana.assignment.domain.LaunchesRepository
import com.rasana.assignment.presentation.view.DetailsActivity
import com.rasana.assignment.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rasana.
 */
@Singleton
@Component(modules = arrayOf(AssignmentModule::class))

interface ApplicationComponent {
    fun inject(assignmentApplication: AssignmentApplication)
    fun inject(activity: MainActivity)
    fun inject(activity: DetailsActivity)

    fun getLaunchesRepository(): LaunchesRepository
}