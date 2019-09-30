package com.rasana.assignment.presentation.presenter

import com.rasana.assignment.domain.AllLaunches
import com.rasana.assignment.domain.LaunchesUseCase
import com.rasana.assignment.presentation.mapDomainToPresentation
import com.rasana.assignment.presentation.mapLaunchesToRow
import com.rasana.assignment.presentation.model.DisplayRow
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import javax.inject.Inject

/**
 * Created by Rasana.
 */
class MainPresenter @Inject constructor(private val launchesUseCase: LaunchesUseCase) {

    private var launchesObservable: Single<List<DisplayRow>>? = null
    private var transferPayeesSubscription = Disposables.disposed()
    private lateinit var displayResults: DisplayResults

    fun inject(displayResults: DisplayResults) {
        this.displayResults = displayResults
    }

    fun onCreate() {
        getLaunchList()
    }

    private fun getLaunchList() {
        launchesObservable = mapPayeeObservableToRows(launchesUseCase.getAllLaunches(), true)
            .doOnSubscribe {}
            .doAfterTerminate {}

        subscribeToPayeeAccounts()
    }

    private fun subscribeToPayeeAccounts() {
        launchesObservable?.let {
            transferPayeesSubscription = it.subscribe(
                { onPayeeSuccess(it) },
                { onPayeeFailure(it) })
        }
    }

    private fun onPayeeSuccess(rows: List<DisplayRow>) {
        displayResults.showLaunches(rows)
    }

    private fun onPayeeFailure(throwable: Throwable) {
        transferPayeesSubscription.dispose()
    }

    private fun mapPayeeObservableToRows(displayItem: Single<List<AllLaunches>>, addHeaders: Boolean): Single<List<DisplayRow>> =
        displayItem.map { it.map { mapDomainToPresentation(it) } }
            .map { mapLaunchesToRow(it, addHeaders) }

    interface DisplayResults {
        fun showLaunches(allLaunches: List<DisplayRow>)
    }
}