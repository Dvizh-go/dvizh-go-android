package com.start.eventgo.main.ui.order.data

import com.start.eventgo.main.ui.order.data.model.TeamData
import com.start.eventgo.main.ui.order.data.model.TicketOrder
import com.start.eventgo.main.ui.order.data.model.TicketOwnerData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketOrderApi {

    @FormUrlEncoded
    @POST("/api/v2/order/set_tickets_amount")
    fun sendTicketCount(
        @Header("Authorization") authorization: String,
        @Field("ticketsAmount") ticketsAmount: Int,
        @Field("datetimeId") datetimeId: Int,
    ): Call<TicketOrder>

    @PUT("/api/v2/order/{ticketOrderId}/set_user_data")
    fun sendTicketOwnerData(
        @Header("Authorization") authorization: String,
        @Path("ticketOrderId") ticketOrderId: Int,
        @Body ticketOwnerData: TicketOwnerData
    ): Call<TicketOrder>

    @PUT("/api/v2/order/{orderId}/set_team_data")
    fun setTeamData(
        @Path("orderId") orderId: Int,
        @Header("Authorization") token: String,
        @Body teamData: TeamData
    ): Call<TicketOrder> // Измените тип возвращаемого значения на свой, если требуется обработка ответа от сервера.
}
