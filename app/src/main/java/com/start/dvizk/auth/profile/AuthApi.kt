package com.start.dvizk.auth.profile

import com.start.dvizk.registration.registr.presentation.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("/api/v2/user/auth")
    fun auth(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>
}
