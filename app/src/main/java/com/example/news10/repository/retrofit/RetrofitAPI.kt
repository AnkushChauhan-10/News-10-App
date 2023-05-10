package com.example.news10.repository.retrofit

import com.example.news10.models.MainNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("top-headlines")
    suspend fun getNews(@Query("country") country:String,
                        @Query("pageSize") pageSize:Int,
                        @Query("apiKey") apiKey: String): Response<MainNews>

    @GET("top-headlines")
    suspend fun getCat(@Query("country") country:String,
                       @Query("category") cat:String,
                       @Query("pageSize") pageSize:Int,
                       @Query("apiKey") apiKey: String): Response<MainNews>

}