package com.example.news10.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.usage.UsageEvents
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.graphics.createBitmap
import androidx.core.view.ContentInfoCompat.Flags
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.multiprocess.IWorkManagerImplCallback.Default
import com.bumptech.glide.Priority
import com.example.news10.MainActivity
import com.example.news10.NewsApplication
import com.example.news10.R
import com.example.news10.models.DaoNewsModel
import com.example.news10.response.ApiResponse
import com.example.news10.utils.NewsNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Version
import java.nio.channels.Channel
import kotlin.random.Random

class NewsWorker(private val context: Context,params: WorkerParameters): Worker(context,params) {
    override fun doWork(): Result {
        val repository = (context as NewsApplication).newsRepository
        CoroutineScope(Dispatchers.IO).launch {
            val response:ApiResponse<DaoNewsModel> = repository.checkNewFeed()
            Log.d("Notification",response.errorMessage.toString())
            if(response is ApiResponse.Success){
                NewsNotification(context).sendNotification(1,applicationContext, response.data!!)
            }
        }
        return Result.success()
    }


}