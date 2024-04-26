package com.example.gymgrove.domain.notification.model

data class AlarmItem(
    val id: Int,
    val message: String,
    val title: String,
    val hour: Int,
    val minute: Int,
    val repeating: Boolean = false
)
