package com.example.gymgrove.data.notification.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.gymgrove.R
import com.example.gymgrove.domain.notification.service.ScheduledNotificationService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ScheduledNotificationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ScheduledNotificationService {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showScheduledNotification(title: String, message: String, id: Int) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify(id, notification)
    }

    companion object {
        const val CHANNEL_ID = "scheduled_notifications_channel_id"
        const val CHANNEL_NAME = "Scheduled Notifications"
    }
}