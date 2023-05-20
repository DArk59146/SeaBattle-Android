package com.example.seabattle.api

import com.example.seabattle.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SeaBattleService {

    private val url = "http://192.168.1.101:8080"

    private var seaBattleApi = createRetrofit().create(SeaBattleApi::class.java)

    fun getApi(): SeaBattleApi {
        return seaBattleApi
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}