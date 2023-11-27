package com.start.dvizk.main.ui.tickets.ticket.data.model

data class UserTicket(
	val ticket: TicketDataModel
)

data class TicketDataModel(
	val id: Int,
	val title: String,
	val datetime_id: Int,
	val event_id: Int,
	val email: String,
	val date: String,
	val time: String,
	val address: TicketLocation,
	val user: TicketUser,
	val price: Int
)

data class TicketLocation(
	val id: Int,
	val country: TicketCountry,
	val city: TicketCity,
	val apartment: String,
	val findingInformation: String?
)

data class TicketCountry(
	val id: Int,
	val name: String,
	val created_at: String?,
	val updated_at: String?
)

data class TicketCity(
	val id: Int,
	val name: String,
	val created_at: String?,
	val updated_at: String?
)

data class TicketUser(
	val id: Int,
	val image: String,
	val name: String,
	val nickname: String,
	val phone_number: String,
	val email: String,
	val gender: String,
	val birthday: String
)