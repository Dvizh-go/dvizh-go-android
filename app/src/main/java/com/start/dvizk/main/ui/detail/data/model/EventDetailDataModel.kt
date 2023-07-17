package com.start.dvizk.main.ui.detail.data.model

data class EventDetailDataModel(
	val id: Int,
	val images: List<String?>?,
	val name: String?,
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
	val organization: Organization?
)

data class DateTime(
	val date: String?,
	val start: String?,
	val duration: String?
)

data class Location(
	val country: String?,
	val city: String?,
	val apartment: String?,
	val findingInformation: String?
)

data class Requirements(
	val age: Int?,
	val children: Boolean,
	val additional_requirements: String?
)

data class Organization(
	val id: Int,
	val image: String?,
	val name: String?,
	val description: String?,
	val phone_number: String?,
	val email: String?,
	val whatsapp: String?,
	val instagram: String?
)