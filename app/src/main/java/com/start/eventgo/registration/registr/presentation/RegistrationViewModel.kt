package com.start.eventgo.registration.registr.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.network.Response
import com.start.eventgo.registration.registr.data.RegistrationRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class RegistrationViewModel(
    private val registrationRepository: RegistrationRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val registrationStateLiveData: MutableLiveData<RegistrationState> = CustomMutableLiveData()

    fun register(
        email: String,
        password: String,
        name: String,
        nickname: String,
        phone_number: String,
        gender: String,
        birthday: String,
        image: MultipartBody.Part?,
    ) {
        registrationStateLiveData.value = RegistrationState.Loading
        launch(Dispatchers.IO) {
            val response = registrationRepository.registr(
                email = email,
                password = password,
                name = name,
                nickname = nickname,
                phone_number = phone_number,
                gender = gender,
                birthday = birthday,
                image = image,
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success -> registrationStateLiveData.value = RegistrationState.Success(response.result)
                    is Response.Error -> registrationStateLiveData.value = RegistrationState.Failed(response.error.toString())
                }
            }
        }
    }
}
