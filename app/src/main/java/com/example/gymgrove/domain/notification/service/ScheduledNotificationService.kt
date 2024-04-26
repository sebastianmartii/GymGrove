package com.example.gymgrove.domain.notification.service

interface ScheduledNotificationService {

    fun showScheduledNotification(title: String, message: String, id: Int)
}