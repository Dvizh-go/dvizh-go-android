package com.start.eventgo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    fun getClient(): Retrofit {

        val client = OkHttpClient().newBuilder().build()
        return Retrofit.Builder()
            .baseUrl("http://eventgo.kz/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
