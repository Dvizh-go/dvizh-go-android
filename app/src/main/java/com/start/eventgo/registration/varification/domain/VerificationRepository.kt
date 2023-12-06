package com.start.eventgo.registration.registr.domain

import com.start.eventgo.network.ApiErrorExceptionFactory
import com.start.eventgo.network.Response
import com.start.eventgo.network.asNewResponse
import com.start.eventgo.registration.registr.presentation.model.User
import com.start.eventgo.registration.varification.data.VerificationApi

class VerificationRepository(
    private val verificationApi: VerificationApi,
    private val apiErrorExceptionFactory: ApiErrorExceptionFactory
) {
    fun verify(
        email: String,
        verificationCode: String
    ): Response<User, Exception> {
        val response = verificationApi
            .verify(
                email = email,
                verificationCode = verificationCode
            )
            .execute()
            .asNewResponse(apiErrorExceptionFactory)

        if (response is Response.Error) return response

        response as Response.Success
        return Response.Success(response.result)
    }
}
