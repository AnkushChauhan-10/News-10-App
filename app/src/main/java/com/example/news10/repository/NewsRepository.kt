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
import kotlin.collections.ArrayList

class NewsRepository(private val roomDao:NewsDao,private val retrofitApi:RetrofitAPI,private val context: Context) {


    fun getNetwork():Flow<NetworkStatus>{
        Log.d("ERROR", "In News Repository class Network permission line 23")
        return PermissionUtil().isInternetAvailable(context)
    }


    fun offLine(category: String) = flow<ApiResponse<List<DaoNewsModel>>>{
        roomDao.getNews(category).catch {
            emit(ApiResponse.Error(it.message.toString()))
        }
            .collect{
                if(it.isNotEmpty()) emit(ApiResponse.Success(it))
                else emit(ApiResponse.Error("Server Error"))
        }
    }

    suspend fun refreshFeed(category: String):Boolean{
        Log.d("Success retro Api", "Start New Feed")
        if(PermissionUtil().checkInternet(context)){
            Log.d("Success retro Api", "Permission check")
            var res = retrofitApi.getCat("in",category,100, Constants.apiKey)
            if (res?.body() != null) {
                Log.d("Success retro Api", "Result Body ${res.body().toString()}")
                res.body()!!.articles.map { it.type = category
                    it.date = convertDate(it.publishedAt) }
                roomDao.insertNews(res.body()!!.articles)
            }
            Log.d("Success retro Api", res.body().toString())
        }
        return true
    }

    private fun convertDate(date: String):Long{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getFrozenTimeZone("GMT")
        return dateFormat.parse(date).time
    }

    suspend fun deleteBackground(){
        var cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR,-5)
        Log.d("Day",cal.time.toString())
        roomDao.delete(cal.time.time)
    }

    suspend fun checkNewFeed():ApiResponse<DaoNewsModel>{
        val result = retrofitApi.getCat("in","sports",100,Constants.apiKey)
        if(result?.body()!=null){
            result.body()!!.articles.forEach {
                if(null != roomDao.checkNews(it.title)){
                    return ApiResponse.Success(it)
                }
            }
        }
        return ApiResponse.Error(result.errorBody().toString())
    }
}