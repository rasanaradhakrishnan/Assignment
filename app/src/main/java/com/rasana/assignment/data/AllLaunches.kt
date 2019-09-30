package com.rasana.assignment.data

/**
 * Created by Rasana.
 */
data class AllLaunches(
    val mission_name: String?,
    val launch_year: String?,
    val launch_date_utc: String?,
    val launch_success: Boolean? = false,
    val details: String?
)