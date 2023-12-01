package com.start.dvizk.main.ui.profile.data.repository

import com.start.dvizk.main.ui.profile.data.api.ProfileApi
import com.start.dvizk.main.ui.profile.data.model.ProfileDataModel
import com.start.dvizk.network.Response
import java.lang.Exception
import org.json.JSONObject

class ProfileRepository(
    private val profileApi: ProfileApi
) {

    fun getUserProfile(
        token: String
    ): Response<ProfileDataModel, String> {
        try {
            val response = profileApi
                .getUserProfile(
                    token = "Bearer $token"
                )
                .execute()

            if (response.isSuccessful) {
                response.body()?.let { return Response.Success(it.user) }
            }
            val message = JSONObject(response.errorBody()?.string()!!).getString("message")

            return Response.Error(message)
        } catch (ex: Exception) {
            return Response.Error(ex.localizedMessage?.toString() ?: "")
        }
    }
}