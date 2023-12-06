package com.start.eventgo.main.ui.tickets.mytickets.data.model.response

import com.start.eventgo.main.ui.tickets.mytickets.data.model.MyTicket

data class UpcomingTicketsResponse(
    val tickets: MutableList<MyTicket>,
    val page: Int,
    val nbTotal: Int,
    val totalPage: Int
)
