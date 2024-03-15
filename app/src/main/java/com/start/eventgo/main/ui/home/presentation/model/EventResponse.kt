package com.start.eventgo.main.ui.home.presentation.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventResponse(
    @JsonProperty("events")
    val events: List<Event>,
    @JsonProperty("nbTotal")
    val nbTotal: Int,
)
