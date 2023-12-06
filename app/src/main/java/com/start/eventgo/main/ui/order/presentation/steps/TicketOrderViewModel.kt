package com.start.eventgo.main.ui.order.presentation.steps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.main.ui.order.data.TicketOrderRepository
import com.start.eventgo.main.ui.order.data.model.TeamData
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketOrderViewModel(
    private val ticketOrderRepository: TicketOrderRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val ticketCountRequestStateLiveData: MutableLiveData<RequestResponseState> =
        CustomMutableLiveData()
    val ticketOwnerDataRequestStateLiveData: MutableLiveData<RequestResponseState> =
        CustomMutableLiveData()
    val ticketTeamDataRequestStateLiveData: MutableLiveData<RequestResponseState> =
        CustomMutableLiveData()

    fun sendTicketCount(
        token: String,
        ticketsAmount: Int,
        datetimeId: Int,
    ) {
        ticketCountRequestStateLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = ticketOrderRepository.sendTicketCount(
                token = token,
                ticketsAmount = ticketsAmount,
                datetimeId = datetimeId,
            )

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        ticketCountRequestStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        ticketCountRequestStateLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
        }
    }

    fun setTeamData(
        token: String,
        orderId: Int,
        teamData: TeamData
    ) {
        ticketTeamDataRequestStateLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = ticketOrderRepository.setTeamData(
                token = token,
                orderId = orderId,
                teamData = teamData,
            )

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        ticketTeamDataRequestStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        ticketTeamDataRequestStateLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
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
    ) {
        ticketOwnerDataRequestStateLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = ticketOrderRepository.sendTicketOwnerData(
                token = "Bearer $token",
                ticketOrderId = ticketOrderId,
                name = name,
                surname = surname,
                email = email,
                birthday = birthday,
                number = number,
            )

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        ticketOwnerDataRequestStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        ticketOwnerDataRequestStateLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
        }
    }
}
