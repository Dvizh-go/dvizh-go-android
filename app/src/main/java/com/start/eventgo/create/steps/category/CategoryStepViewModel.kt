package com.start.eventgo.create.steps.category

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

class CategoryStepViewModel(
    private val eventCreateRepository: EventCreateRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val requestSendCatResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

    fun sendEventCat(
        token: String,
        catList: List<Int>,
        eventId: Int,
        numberStep: Int,
    ) {
        requestSendCatResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            val response = eventCreateRepository.sendEventCategory(
                token = token,
                catList = catList,
                eventId = eventId,
                numberStep = numberStep,
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        requestSendCatResponseStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        requestSendCatResponseStateLiveData.value =
                            RequestResponseState.Failed(response.error.toString())
                }
            }
        }
    }
}
