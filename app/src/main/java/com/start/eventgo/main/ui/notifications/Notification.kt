package com.start.eventgo.main.ui.notifications

data class Notification(
    val icon: Int,
    val notViewed: Boolean,
    val title: String,
    val subtitle: String,
    val description: String
)
