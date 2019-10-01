package com.rasana.assignment.presentation.presenter

import android.util.Log
import com.rasana.assignment.domain.AllLaunches
import com.rasana.assignment.domain.LaunchesUseCase
import com.rasana.assignment.presentation.mapDomainToPresentation
import com.rasana.assignment.presentation.mapLaunchesToRow
import com.rasana.assignment.presentation.mapLaunchesToRowByDate
import com.rasana.assignment.presentation.model.DisplayRow
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import javax.inject.Inject

/**
 * Created by Rasana.
 */
class MainPresenter @Inject constructor(private val launchesUseCase: LaunchesUseCase) {

    private var launchesObservable: Single<List<DisplayRow>>? = null
    private var launchesSubscription = Disposables.disposed()
    private lateinit var displayResults: DisplayResults

    private companion object {
        const val SORT_BY_NAME = "mission_name"
        const val SORT_BY_DATE = "launch_date_utc"
    }

    fun inject(displayResults: DisplayResults) {
        this.displayResults = displayResults
    }

    fun onCreate() = getLaunchList(SORT_BY_NAME)

    fun onPause() = launchesSubscription.dispose()

    fun onStop() {
        launchesObservable = null
    }

    fun onClickSortByName() = getLaunchList(SORT_BY_NAME)

    fun onClickSortByDate() = getLaunchList(SORT_BY_DATE)

    private fun getLaunchList(sortOrder: String) {
        launchesObservable = mapPayeeObservableToRows(launchesUseCase.getAllLaunches(sortOrder), true, sortOrder)
            .doOnSubscribe { displayResults.showLoading() }
            .doAfterTerminate {
                displayResults.hideLoading()
                launchesObservable = null
            }

        subscribeLaunches()
    }

    private fun subscribeLaunches() =
        launchesObservable?.let {
            if (launchesSubscription.isDisposed) {
                launchesSubscription = it.subscribe(
                    { onPayeeSuccess(it) },
                    { onPayeeFailure(it) })
            }
        }

    private fun onPayeeSuccess(rows: List<DisplayRow>) = displayResults.showLaunches(rows)

    private fun onPayeeFailure(throwable: Throwable) {
        Log.d("Exception to be handled", throwable.toString())
    }

    private fun mapPayeeObservableToRows(
        displayItem: Single<List<AllLaunches>>,
        addHeaders: Boolean,
        sortOrder: String
    ): Single<List<DisplayRow>> =
        if (sortOrder.equals(SORT_BY_NAME)) {
            displayItem.map { it.map { mapDomainToPresentation(it) } }
                .map { mapLaunchesToRow(it, addHeaders) }
        } else {
            displayItem.map { it.map { mapDomainToPresentation(it) } }
                .map { mapLaunchesToRowByDate(it, addHeaders) }
        }

    interface DisplayResults {
        fun showLaunches(allLaunches: List<DisplayRow>)

        fun showLoading()
        fun hideLoading()
    }
}