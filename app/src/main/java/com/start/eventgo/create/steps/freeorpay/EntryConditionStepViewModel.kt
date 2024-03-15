package com.start.eventgo.create.steps.freeorpay

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

class EntryConditionStepViewModel(
    private val eventCreateRepository: EventCreateRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

    fun sendEventEntryCondition(
        token: String,
        numberStep: Int,
        classification: String,
        eventId: Int,
    ) {
        requestResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            val response = eventCreateRepository.sendEventEntryCondition(
                token = token,
                numberStep = numberStep,
                entry_condition = classification,
                eventId = eventId,
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
