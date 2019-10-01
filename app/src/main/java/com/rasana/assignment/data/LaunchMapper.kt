package com.rasana.assignment.data

import com.rasana.assignment.domain.AllLaunches as DomainLaunches

/**
 * Created by Rasana.
 */
fun mapDataToDomainLaunches(allLaunches: List<AllLaunches>): List<DomainLaunches> {
    val domainLaunches = mutableListOf<DomainLaunches>()
    allLaunches.forEach {
        domainLaunches.add(
            DomainLaunches(
                it.mission_name,
                it.launch_year,
                it.launch_date_utc,
                it.launch_success,
                it.details,
                it.rocket?.rocket_name,
                it.rocket?.rocket_type,
                it.links?.wikipedia
            )
        )
    }
    return domainLaunches
}