package com.start.eventgo.main.ui.home.presentation.model

sealed class PopularEventsState {

    object Loading : PopularEventsState()

    data class Failed(
        val message: String
    ) : PopularEventsState()

    data class Success(
        val events: List<Event>
    ) : PopularEventsState()
}
