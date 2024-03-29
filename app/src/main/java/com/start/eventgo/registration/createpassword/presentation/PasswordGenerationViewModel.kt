package com.start.eventgo.registration.createpassword.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.network.Response
import com.start.eventgo.registration.createpassword.domain.PasswordGenerationRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PasswordGenerationViewModel(
    private val passwordGenerationRepository: PasswordGenerationRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val passwordGenerationStateLiveData: MutableLiveData<PasswordGenerationState> = CustomMutableLiveData()

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
        passwordGenerationStateLiveData.value = PasswordGenerationState.Loading
        launch(Dispatchers.IO) {
            val response = passwordGenerationRepository.registr(
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
                    is Response.Success ->
                        passwordGenerationStateLiveData.value =
                            PasswordGenerationState.Success(response.result)
                    is Response.Error ->
                        passwordGenerationStateLiveData.value =
                            PasswordGenerationState.Failed(response.error)
                }
            }
        }
    }
}
