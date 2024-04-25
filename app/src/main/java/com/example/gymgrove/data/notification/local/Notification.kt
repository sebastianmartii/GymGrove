package com.example.gymgrove.data.notification.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val message: String,
    val hour: Int,
    val minute: Int,
    val repeating: Boolean
)
