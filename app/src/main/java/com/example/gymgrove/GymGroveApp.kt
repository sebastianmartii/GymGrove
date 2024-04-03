package com.example.gymgrove

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.gymgrove.data.notification.service.ScheduledNotificationServiceImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GymGroveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            ScheduledNotificationServiceImpl.CHANNEL_ID,
            ScheduledNotificationServiceImpl.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}