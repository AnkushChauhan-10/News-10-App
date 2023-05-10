package com.example.news10

import android.app.Application
import com.example.news10.repository.NewsRepository
import com.example.news10.repository.retrofit.RetrofitAPI
import com.example.news10.repository.retrofit.RetrofitHelper
import com.example.news10.repository.room.NewsDataBase

class NewsApplication:Application() {

    lateinit var newsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        val dao = NewsDataBase.getDatabase(this)
        val api = RetrofitHelper.getInstance().create(RetrofitAPI::class.java)
        newsRepository = NewsRepository(dao.getDao(),api,this.applicationContext)
    }

}