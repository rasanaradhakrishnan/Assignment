package com.rasana.assignment.data

import com.rasana.assignment.domain.AllLaunches as DomainLaunches

/**
 * Created by Rasana.
 */
fun mapDataToDomainLaunches(allLaunches: List<AllLaunches>): List<DomainLaunches> {
    val domainLaunches = mutableListOf<DomainLaunches>()
    allLaunches.forEach {
        domainLaunches.add(DomainLaunches(it.missionName, it.launchYear, it.launchDate, it.launchSuccess, it.details))
    }
    return domainLaunches
}