package com.example.news10.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.news10.NewsApplication
import com.example.news10.models.DaoNewsModel
import com.example.news10.utils.NewsNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OneTimeNewsWorker(private val context: Context,params: WorkerParameters):Worker(context,params) {

    override fun doWork(): Result {
        val repository = (applicationContext as NewsApplication).newsRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteBackground()
            Log.d("Notification","repo")
        }
        NewsNotification(context).sendNotification(1,applicationContext, DaoNewsModel("gen","News Title","ankush",
            "skjds ksks k sjkd skjdks kj dkjdklsdkf","as","sa","asad",1233))
        return Result.success()
    }
}