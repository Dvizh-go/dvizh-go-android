package com.start.eventgo.main.ui.home.presentation.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Category(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("parent_id")
    val parent_id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("image")
    val image: String,
    @JsonProperty("is_sub")
    val is_sub: Boolean,
    var isSelected: Boolean = false,
    val isEmpty: Boolean = false,
)
