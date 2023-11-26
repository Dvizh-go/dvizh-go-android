package com.start.dvizk.main.ui.profile.data.model

import com.start.dvizk.main.ui.home.presentation.model.Event

data class AvailableEvents(
    val events: List<Event>,
    val page: Int,
    val nbTotal: Int,
    val totalPage: Int,
)
