package com.start.eventgo.create.steps.calendar

import com.start.eventgo.create.steps.calendar.model.EventDateTimeInterval

interface OnSelectTimeListener {

    fun onStartTimeSelect(item: EventDateTimeInterval)

    fun onDurationTimeSelect(item: EventDateTimeInterval)
}
