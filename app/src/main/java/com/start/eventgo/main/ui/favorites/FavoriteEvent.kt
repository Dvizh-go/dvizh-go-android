package com.start.eventgo.main.ui.favorites

data class FavoriteEvent(
    val image: Int,
    val title: String,
    val date: String,
    val location: String,
    var isFavorite: Boolean
)
