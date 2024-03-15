package com.start.eventgo.registration.varification.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.network.Response
import com.start.eventgo.registration.registr.domain.VerificationRepository
import com.start.eventgo.registration.registr.presentation.model.User
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerificationViewModel(
    private val verificationRepo: VerificationRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val verificationStateLiveData: MutableLiveData<VerificationState> = CustomMutableLiveData()

    fun verify(
        email: String,
        verificationCode: String
    ) {
        verificationStateLiveData.value = VerificationState.Loading
        launch(Dispatchers.IO) {
            val response = verificationRepo.verify(
                email = email,
                verificationCode = verificationCode
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success -> {
                        setUserData(response.result)
                        verificationStateLiveData.value =
                            VerificationState.Success(null)
                    }
                    is Response.Error ->
                        verificationStateLiveData.value =
                            VerificationState.Failed(response.error.toString())
                }
            }
        }
    }

    private fun setUserData(user: User) {
        sharedPreferencesRepository.setUserToken(user.token)
        user.id?.let {
            sharedPreferencesRepository.setUserId(it)
        }
        sharedPreferencesRepository.setUserName(user.name)
        user.image?.let { it1 ->
            sharedPreferencesRepository.setUserImage(
                it1
            )
        }
    }
}
