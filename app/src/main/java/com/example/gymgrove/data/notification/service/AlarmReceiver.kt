package com.example.gymgrove.data.notification.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.gymgrove.data.notification.local.NotificationDao
import com.example.gymgrove.domain.notification.service.ScheduledNotificationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduledNotificationsService: ScheduledNotificationService

    @Inject
    lateinit var notificationDao: NotificationDao

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        val message = intent?.getStringExtra(AndroidAlarmScheduler.EXTRA_MESSAGE)
        val title = intent?.getStringExtra(AndroidAlarmScheduler.EXTRA_TITLE)
        val id = intent?.getIntExtra(AndroidAlarmScheduler.EXTRA_ID, 1)
        val repeating = intent?.getBooleanExtra(AndroidAlarmScheduler.EXTRA_REPEATING, false) ?: false
        if (title == null || message == null || id == null) return@goAsync
        scheduledNotificationsService.showScheduledNotification(
            title = title,
            message = message,
            id = id
        )
        if (!repeating) {
            notificationDao.delete(id)
        }
    }
}

private fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}