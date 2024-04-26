package com.example.gymgrove.data.notification

import com.example.gymgrove.domain.notification.model.AlarmItem
import com.example.gymgrove.data.notification.local.Notification as NotificationEntity
import com.example.gymgrove.domain.notification.model.Notification as NotificationModel

fun NotificationModel.toNotificationEntity(): NotificationEntity {
    return NotificationEntity(
        id = id,
        title = title,
        message = message,
        hour = hour,
        minute = minute,
        repeating = repeating
    )
}

fun NotificationEntity.toNotificationModel(): NotificationModel {
    return NotificationModel(
        id = id!!,
        title = title,
        message = message,
        hour = hour,
        minute = minute,
        repeating = repeating
    )
}

fun NotificationModel.toAlarmItem(): AlarmItem {
    return AlarmItem(
        id, message, title, hour, minute, repeating
    )
}