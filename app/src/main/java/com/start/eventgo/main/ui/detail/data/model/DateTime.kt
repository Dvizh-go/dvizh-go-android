package com.start.eventgo.main.ui.detail.data.model

data class DateTime(
    val id: Int,
    val date: String?,
    val start: String?,
    val duration: String?,
    var isSelected: Boolean = false
)
