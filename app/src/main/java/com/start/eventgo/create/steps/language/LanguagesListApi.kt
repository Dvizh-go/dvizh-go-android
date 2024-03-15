package com.start.eventgo.create.steps.language

import com.start.eventgo.create.steps.language.model.EventParameter
import retrofit2.Call
import retrofit2.http.GET

interface LanguagesListApi {

    @GET("/api/v2/language")
    fun getLanguages(): Call<MutableList<EventParameter>>
}
