package com.rasana.assignment.presentation.presenter

import com.rasana.assignment.presentation.model.DisplayLaunches
import javax.inject.Inject

class DetailsPresenter @Inject constructor() {

    private lateinit var displayResults: DisplayResults
    private lateinit var detailsText: DisplayLaunches

    fun inject(displayResults: DisplayResults) {
        this.displayResults = displayResults
    }

    fun onIntentReceived(details: DisplayLaunches) {
        detailsText = details
    }

    fun onCreate() = displayResults.showDetails(detailsText)

    interface DisplayResults {
        fun showDetails(details: DisplayLaunches)

        fun showLoading()
        fun hideLoading()
    }
}