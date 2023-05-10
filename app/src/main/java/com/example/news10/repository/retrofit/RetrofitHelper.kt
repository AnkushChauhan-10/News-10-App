package com.example.news10.repository.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitHelper {

    private val base_url = "https://newsapi.org/v2/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}