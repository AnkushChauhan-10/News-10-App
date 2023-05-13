package com.example.news10

import android.annotation.SuppressLint
import android.app.Application
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.work.*
import com.example.news10.repository.NewsRepository
import com.example.news10.repository.retrofit.RetrofitAPI
import com.example.news10.repository.retrofit.RetrofitHelper
import com.example.news10.repository.room.NewsDataBase
import com.example.news10.worker.NewsWorker
import com.example.news10.worker.OneTimeNewsWorker
import java.util.concurrent.TimeUnit

class NewsApplication:Application() {

    lateinit var newsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
        setUpWorker()
    }

    private fun initialize(){
        val dao = NewsDataBase.getDatabase(this)
        val api = RetrofitHelper.getInstance().create(RetrofitAPI::class.java)
        newsRepository = NewsRepository(dao.getDao(),api,this.applicationContext)
    }


    private fun setUpWorker(){
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest1 = OneTimeWorkRequest.Builder(OneTimeNewsWorker::class.java).setConstraints(constraint).build()
        val workerRequest2 = PeriodicWorkRequest.Builder(NewsWorker::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workerRequest2)
        WorkManager.getInstance(this).enqueue(workRequest1)
    }
}