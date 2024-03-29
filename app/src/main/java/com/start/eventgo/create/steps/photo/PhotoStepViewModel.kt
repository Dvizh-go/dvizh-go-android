package com.start.eventgo.create.steps.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.steps.data.EventCreateRepository
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PhotoStepViewModel(
    private val eventCreateRepository: EventCreateRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val requestMainImageAddResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
    val requestAdditionalImageAddResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()
    val requestResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

    fun sendEventMainImage(
        authorization: String,
        image: MultipartBody.Part?,
        eventId: Int,
    ) {
        requestMainImageAddResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            val response = eventCreateRepository.sendEventMainImage(
                authorization = authorization,
                image = image,
                eventId = eventId,
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        requestMainImageAddResponseStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        requestMainImageAddResponseStateLiveData.value =
                            RequestResponseState.Failed(response.error.toString())
                }
            }
        }
    }

    fun sendEventAdditionalImage(
        authorization: String,
        image: MultipartBody.Part?,
        eventId: Int,
    ) {
        requestAdditionalImageAddResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            delay(1000)
            val response = eventCreateRepository.sendEventAdditionalImage(
                authorization = authorization,
                image = image,
                eventId = eventId,
            )
            delay(1000)
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        requestAdditionalImageAddResponseStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        requestAdditionalImageAddResponseStateLiveData.value =
                            RequestResponseState.Failed(response.error.toString())
                }
            }
        }
    }

    fun sendEventImage(
        authorization: String,
        numberStep: Int,
        eventId: Int,
    ) {
        requestResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            delay(1000)
            val response = eventCreateRepository.sendEventImage(
                token = authorization,
                numberStep = numberStep,
                eventId = eventId,
            )
            delay(1000)
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
