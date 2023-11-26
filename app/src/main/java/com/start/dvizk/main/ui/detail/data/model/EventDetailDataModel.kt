package com.start.dvizk.main.ui.detail.data.model

data class EventDetailDataModel(
    val id: Int,
    val images: List<String?>?,
    val name: String?,
    val datetimes: List<DateTime>?,
    val datetime: DateTime?,
    val languages: List<String?>?,
    val price: String?,
    val location: Location?,
    val requirements: Requirements?,
    val description: String?,
    val additional_services: String?,
    val necessary_items: List<String?>?,
    val entry_condition: String?,
    val is_favorite: Boolean,
    val organization: Organization?,
    val team: Team?,
)
