package com.start.eventgo.search.search.presentation

import com.start.eventgo.search.search.presentation.model.DateRange
import com.start.eventgo.search.search.presentation.model.MonthModel

interface SelectedParams {

    fun onDateRangeSelected(dateRange: DateRange)

    fun onMonthListSelected(month: MonthModel)
}
