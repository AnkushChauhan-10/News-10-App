package com.example.news10.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.graphics.createBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.news10.MainActivity
import com.example.news10.R
import com.example.news10.models.DaoNewsModel

class NewsNotification(private val context: Context) {

    fun sendNotification(id: Int, applicationContext: Context,news:DaoNewsModel){
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(Notification.EXTRA_NOTIFICATION_ID,id)
        }
        val remoteViews = customRemoteView(news)
        val notificationManager = applicationContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        val bitmap = applicationContext.vectorToBitmap(R.drawable.baseline_signal_wifi_off_24)
        val title = news.title
        val subTitle = news.description
        val pendingIntent = PendingIntent.getActivities(applicationContext,0, arrayOf(intent),0)

        val notification = NotificationCompat.Builder(applicationContext,"i.app.News10")
            .setSmallIcon(R.drawable.ic_stat_name)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(remoteViews)
            .setDefaults(0).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = Notification.PRIORITY_MAX

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notification.setChannelId("i.app.News10")
            val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttr = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

            val channel = NotificationChannel("i.app.News10","News", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100,200,300,400,500,400,300,200,400)
            channel.setSound(ringtoneManager,audioAttr)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(id, notification.build())
        Log.d("Notification","mksasnndjnsjnj")
    }

    fun Context.vectorToBitmap(drawableId: Int): Bitmap?{
        val drawable = getDrawable(drawableId)?:return null
        val bitmap = createBitmap(
            drawable.intrinsicWidth,drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        ) ?:return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,canvas.width,canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    @SuppressLint("RemoteViewLayout")
    fun customRemoteView(data: DaoNewsModel):RemoteViews{
        val remoteView = RemoteViews(context.packageName,R.layout.custom_notification)
        Glide.with(context).asBitmap().load(data.urlToImage).into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                remoteView.setImageViewBitmap(R.id.notifyImg,resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })
        remoteView.setImageViewResource(R.id.notifyImg,R.drawable.baseline_broken_image_24)
        remoteView.setTextViewText(R.id.headLine,data.title)
        remoteView.setTextViewText(R.id.content,data.description)
        return remoteView
    }

}