package com.start.eventgo.main.ui.profile.data.model

import com.start.eventgo.main.ui.home.presentation.model.Event

data class AvailableEvents(
    val events: List<Event>,
    val page: Int,
    val nbTotal: Int,
    val totalPage: Int,
)
