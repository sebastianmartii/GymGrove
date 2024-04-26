package com.example.gymgrove.data.notification.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert
    suspend fun insert(notification: Notification)

    @Transaction
    suspend fun insertAndGetId(notification: Notification): Int {
        insert(notification)
        return getNewEntryId()
    }

    @Delete
    suspend fun delete(notification: Notification)

    @Query("DELETE FROM notification_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Update
    suspend fun update(notification: Notification)

    @Query("SELECT max(id) FROM notification_table")
    suspend fun getNewEntryId(): Int

    @Query("SELECT * FROM notification_table")
    fun getNotifications(): Flow<List<Notification>>

    @Query("SELECT * FROM notification_table WHERE id = :id")
    suspend fun getNotification(id: Int): Notification
}
