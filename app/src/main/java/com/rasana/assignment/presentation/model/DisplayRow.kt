package com.rasana.assignment.presentation.model

/**
 * Created by Rasana.
 */
sealed class DisplayRow{
    class Header(val headerTitle: String) : DisplayRow()
    class DisplayItem(val displayItem: DisplayLaunches) : DisplayRow()
}