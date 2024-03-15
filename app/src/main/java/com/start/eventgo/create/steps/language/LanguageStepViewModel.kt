package com.start.eventgo.create.steps.language

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

class LanguageStepViewModel(
    private val eventCreateRepository: EventCreateRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
    val requestSendLangResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

    fun getLanguages() {
        requestResponseStateLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = eventCreateRepository.getLanguages()

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

    fun sendEventLanguages(
        token: String,
        languages: List<Int>,
        eventId: Int,
        numberStep: Int,
    ) {
        requestSendLangResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            val response = eventCreateRepository.sendEventLanguages(
                token = token,
                languages = languages,
                eventId = eventId,
                numberStep = numberStep,
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        requestSendLangResponseStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        requestSendLangResponseStateLiveData.value =
                            RequestResponseState.Failed(response.error.toString())
                }
            }
        }
    }
}
