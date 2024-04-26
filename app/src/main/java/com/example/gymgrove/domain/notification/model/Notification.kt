package com.example.gymgrove.domain.notification.model

data class Notification(
    val id: Int,
    val message: String,
    val hour: Int,
    val minute: Int,
    val title: String,
    val repeating: Boolean
)
