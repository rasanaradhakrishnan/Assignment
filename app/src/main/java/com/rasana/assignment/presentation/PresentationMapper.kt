package com.rasana.assignment.presentation

import com.rasana.assignment.domain.AllLaunches
import com.rasana.assignment.presentation.model.DisplayLaunches
import com.rasana.assignment.presentation.model.DisplayRow

/**
 * Created by Rasana.
 */

fun mapDomainToPresentation(launches: AllLaunches): DisplayLaunches =
    launches.run {
        DisplayLaunches(missionName,
            launchYear,
            launchDate,
            launchSuccess,
            details)
    }

fun mapLaunchesToRow(launches: List<DisplayLaunches>, addHeaders: Boolean): List<DisplayRow> {
    var currentSectionTitle: Char? = null
    val payeeRows = mutableListOf<DisplayRow>()

    launches.forEach {
        if (addHeaders && it.missionName?.first()?.toUpperCase() != currentSectionTitle) {
            currentSectionTitle = it.missionName?.first()?.toUpperCase()
            payeeRows.add(DisplayRow.Header(currentSectionTitle.toString()))
        }
        payeeRows.add(DisplayRow.DisplayItem(it))
    }

    return payeeRows
}

fun mapLaunchesToRowByDate(launches: List<DisplayLaunches>, addHeaders: Boolean): List<DisplayRow> {
    var currentSectionTitle: String? = null
    val payeeRows = mutableListOf<DisplayRow>()

    launches.forEach {
        if (addHeaders && it.launchDate != currentSectionTitle) {
            currentSectionTitle = it.launchDate
            payeeRows.add(DisplayRow.Header(currentSectionTitle.toString()))
        }
        payeeRows.add(DisplayRow.DisplayItem(it))
    }

    return payeeRows
}