package com.rasana.assignment.data

/**
 * Created by Rasana.
 */
data class AllLaunches(
    val mission_name: String?,
    val launch_year: String?,
    val launch_date_utc: String?,
    val launch_success: Boolean? = false,
    val details: String?,
    val rocket: Rocket?,
    val links: Links?
)

class Rocket(val rocket_name: String?,
             val rocket_type: String?)

class Links(val wikipedia: String?)