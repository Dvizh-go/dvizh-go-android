package com.start.eventgo.create.steps.price

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.steps.data.EventCreateRepository
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PriceStepViewModel(
    private val eventCreateRepository: EventCreateRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

    fun sendEventPrice(
        token: String,
        disList: List<String>,
        eventId: Int,
        price: Int,
        commission: Float,
        numberStep: Int,
    ) {
        requestResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            val response = eventCreateRepository.sendEventPrice(
                commission = commission,
                price = price,
                eventId = eventId,
                disList = disList,
                token = token,
                numberStep = numberStep
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        requestResponseStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        requestResponseStateLiveData.value =
                            RequestResponseState.Failed(response.error.toString())
                }
            }
        }
    }
}
