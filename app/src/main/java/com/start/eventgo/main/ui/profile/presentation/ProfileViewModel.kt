package com.start.eventgo.main.ui.profile.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.main.ui.profile.data.repository.ProfileRepository
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val profileStateLiveData: MutableLiveData<RequestResponseState>	= CustomMutableLiveData()

    fun getUserProfile(token: String) {
        launch(Dispatchers.IO) {
            val response = profileRepository.getUserProfile(token)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        profileStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        profileStateLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
        }
    }
}
