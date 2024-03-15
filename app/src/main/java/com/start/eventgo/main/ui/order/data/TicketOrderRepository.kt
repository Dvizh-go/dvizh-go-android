package com.start.eventgo.main.ui.order.data

import com.start.eventgo.main.ui.order.data.model.TeamData
import com.start.eventgo.main.ui.order.data.model.TicketOrder
import com.start.eventgo.main.ui.order.data.model.TicketOwnerData
import com.start.eventgo.network.ApiErrorExceptionFactory
import com.start.eventgo.network.Response
import org.json.JSONObject

class TicketOrderRepository(
    private val ticketOrderApi: TicketOrderApi,
    private val apiErrorExceptionFactory: ApiErrorExceptionFactory
) {

    fun sendTicketCount(
        token: String,
        ticketsAmount: Int,
        datetimeId: Int,
    ): Response<TicketOrder, String> {
        try {
            val response = ticketOrderApi
                .sendTicketCount(
                    authorization = "Bearer $token",
                    ticketsAmount = ticketsAmount,
                    datetimeId = datetimeId,
                )
                .execute()

            if (response.isSuccessful) {

                return Response.Success(response.body()!!)
            }
            val message = JSONObject(response.errorBody()?.string()!!).getString("message")

            return Response.Error(message)
        } catch (e: Exception) {
            return Response.Error(e.localizedMessage!!.toString())
        }
    }

    fun sendTicketOwnerData(
        token: String,
        ticketOrderId: Int,
        name: String,
        surname: String,
        email: String,
        birthday: String,
        number: String,
    ): Response<TicketOrder, String> {
        try {
            val response = ticketOrderApi
                .sendTicketOwnerData(
                    authorization = "Bearer $token",
                    ticketOrderId = ticketOrderId,
                    ticketOwnerData = TicketOwnerData(
                        name = name,
                        surname = surname,
                        email = email,
                        birthday = birthday,
                        number = number,
                    )
                )
                .execute()

            if (response.isSuccessful) {

                return Response.Success(response.body()!!)
            }
            val message = JSONObject(response.errorBody()?.string()!!).getString("message")

            return Response.Error(message)
        } catch (e: Exception) {
            return Response.Error(e.localizedMessage!!.toString())
        }
    }

    fun setTeamData(
        token: String,
        orderId: Int,
        teamData: TeamData
    ): Response<TicketOrder, String> {
        try {
            val response = ticketOrderApi
                .setTeamData(
                    token = "Bearer $token",
                    orderId = orderId,
                    teamData = teamData,
                )
                .execute()

            if (response.isSuccessful) {

                return Response.Success(response.body()!!)
            }
            val message = JSONObject(response.errorBody()?.string()!!).getString("message")

            return Response.Error(message)
        } catch (e: Exception) {
            return Response.Error(e.localizedMessage!!.toString())
        }
    }
}
