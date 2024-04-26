package com.example.gymgrove.domain.notification.repository

import com.example.gymgrove.domain.notification.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun getNotifications(): Flow<List<Notification>>
    suspend fun getNotification(id: Int): Notification
    suspend fun scheduleNotification(hour: Int, minute: Int, title: String, message: String, repeating: Boolean = false): Int
    suspend fun updateNotification(id: Int, hour: Int, minute: Int, title: String, message: String, repeating: Boolean = false)
    suspend fun deleteNotification(notification: Notification)
}