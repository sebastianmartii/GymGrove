package com.example.gymgrove.data.notification.repository

import com.example.gymgrove.data.notification.local.NotificationDao
import com.example.gymgrove.data.notification.toNotificationEntity
import com.example.gymgrove.data.notification.toNotificationModel
import com.example.gymgrove.domain.notification.model.Notification
import com.example.gymgrove.domain.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.gymgrove.data.notification.local.Notification as NotificationEntity

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override fun getNotifications(): Flow<List<Notification>> {
        return notificationDao.getNotifications().map { notifications ->
            notifications.map {
                it.toNotificationModel()
            }
        }
    }

    override suspend fun getNotification(id: Int): Notification {
        return notificationDao.getNotification(id).toNotificationModel()
    }

    override suspend fun scheduleNotification(hour: Int, minute: Int, title: String, message: String, repeating: Boolean): Int {
        return notificationDao.insertAndGetId(
            NotificationEntity(
                title = title,
                message = message,
                hour = hour,
                minute = minute,
                repeating = repeating
            )
        )
    }

    override suspend fun updateNotification(
        id: Int,
        hour: Int,
        minute: Int,
        title: String,
        message: String,
        repeating: Boolean
    ) {
        notificationDao.update(
            NotificationEntity(
                id = id,
                hour = hour,
                minute = minute,
                title = title,
                message = message,
                repeating = repeating
            )
        )
    }

    override suspend fun deleteNotification(notification: Notification) {
        notificationDao.delete(notification.toNotificationEntity())
    }
}