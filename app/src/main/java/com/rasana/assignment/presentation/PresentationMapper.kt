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
            details,
            rocketName,
            rocketType,
            wikipediaLink)
    }

fun mapLaunchesToRow(launches: List<DisplayLaunches>, addHeaders: Boolean): List<DisplayRow> {
    var currentSectionTitle: Char? = null
    val launchesRows = mutableListOf<DisplayRow>()

    launches.forEach {
        if (addHeaders && it.missionName?.first()?.toUpperCase() != currentSectionTitle) {
            currentSectionTitle = it.missionName?.first()?.toUpperCase()
            launchesRows.add(DisplayRow.Header(currentSectionTitle.toString()))
        }
        launchesRows.add(DisplayRow.DisplayItem(it))
    }

    return launchesRows
}

fun mapLaunchesToRowByDate(launches: List<DisplayLaunches>, addHeaders: Boolean): List<DisplayRow> {
    var currentSectionTitle: String? = null
    val launchesRows = mutableListOf<DisplayRow>()

    launches.forEach {
        if (addHeaders && !it.launchYear.equals(currentSectionTitle)) {
            currentSectionTitle = it.launchYear
            launchesRows.add(DisplayRow.Header(currentSectionTitle.toString()))
        }
        launchesRows.add(DisplayRow.DisplayItem(it))
    }

    return launchesRows
}