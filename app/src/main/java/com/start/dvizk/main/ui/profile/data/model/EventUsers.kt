package com.start.dvizk.main.ui.profile.data.model

data class EventUsers(
    val classification: String,
    val usersData: List<EventUser>,
    val page: Int,
    val totalPage: Int,
    val nbTotal: Int,
)
