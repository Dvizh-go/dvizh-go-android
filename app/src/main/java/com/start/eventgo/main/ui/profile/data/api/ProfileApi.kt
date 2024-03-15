package com.start.eventgo.main.ui.profile.data.api

import com.start.eventgo.main.ui.profile.data.model.UserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {

    @GET("/api/v2/user_profile")
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<UserProfile>
}
