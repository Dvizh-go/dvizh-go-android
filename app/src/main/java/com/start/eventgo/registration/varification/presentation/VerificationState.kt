package com.start.eventgo.registration.varification.presentation

import com.google.gson.JsonObject

sealed class VerificationState {

    object Loading : VerificationState()

    data class Failed(
        val message: String
    ) : VerificationState()

    data class Success(
        val value: JsonObject?
    ) : VerificationState()
}
