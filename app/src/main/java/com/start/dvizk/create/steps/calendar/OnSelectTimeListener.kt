package com.start.dvizk.create.steps.calendar

import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval

interface OnSelectTimeListener {

    fun onStartTimeSelect(item: EventDateTimeInterval)

    fun onDurationTimeSelect(item: EventDateTimeInterval)
}
