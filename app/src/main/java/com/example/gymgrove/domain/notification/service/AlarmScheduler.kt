package com.example.gymgrove.domain.notification.service

import com.example.gymgrove.domain.notification.model.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)

    fun cancel(item: AlarmItem)
}