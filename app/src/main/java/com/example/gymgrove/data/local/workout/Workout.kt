package com.example.gymgrove.data.local.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val durationInMinutes: Int,
    val totalWorkingSetsDone: Int,
    val dateString: String,
)
