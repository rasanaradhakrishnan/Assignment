package com.rasana.assignment.application

import android.app.Application

/**
 * Created by Rasana Radhakrishnan on 2019-09-29.
 */
class AssignmentApplication: Application() {
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .assignmentModule(AssignmentModule())
            .build()

        applicationComponent.inject(this)
    }

}