package com.rasana.assignment.presentation.model

import java.io.Serializable

/**
 * Created by Rasana.
 */
class DisplayLaunches(val missionName: String?,
                      val launchYear: String?,
                      val launchDate: String?,
                      val launchSuccess: Boolean? = false,
                      val details: String?,
                      val rocketName: String?= "",
                      val rocketType: String? = "",
                      val wikipediaLink: String? = ""): Serializable