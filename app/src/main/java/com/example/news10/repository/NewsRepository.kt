package com.example.news10.repository

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import com.example.news10.models.DaoNewsModel
import com.example.news10.repository.retrofit.RetrofitAPI
import com.example.news10.repository.room.NewsDao
import com.example.news10.response.ApiResponse
import com.example.news10.response.NetworkStatus
import com.example.news10.utils.Constants
import com.example.news10.utils.PermissionUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

class NewsRepository(private val roomDao:NewsDao,private val retrofitApi:RetrofitAPI,private val context: Context) {


    fun getNetwork():Flow<NetworkStatus>{
        return PermissionUtil().isInternetAvailable(context)
    }


    fun offLine(category: String) = flow<ApiResponse<List<DaoNewsModel>>>{
        roomDao.getNews(category).catch {
            emit(ApiResponse.Error(it.message.toString()))
        }
            .collect{
            emit(ApiResponse.Success(it))
        }
    }

    suspend fun refreshFeed(category: String):Boolean{
        if(PermissionUtil().checkInternet(context)){
            var res = retrofitApi.getCat("in",category,100, Constants.apiKey)
            if (res?.body() != null) {
                res.body()!!.articles.map { it.type = category
                    it.date = convertDate(it.publishedAt) }
                roomDao.insertNews(res.body()!!.articles)
            }
        }
        return true
    }

    private fun convertDate(date: String):Long{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getFrozenTimeZone("GMT")
        return dateFormat.parse(date).time
    }
}