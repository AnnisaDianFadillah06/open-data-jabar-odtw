package com.example.praktikumpertama.repository

import com.example.praktikumpertama.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WisataRepository {
    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.jabarprov.go.id/api-backend/bigdata/") // URL dasar API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    fun getWisataList() = api.getWisataList()
}
