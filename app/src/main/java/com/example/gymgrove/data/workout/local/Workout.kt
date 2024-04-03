package com.example.gymgrove.data.workout.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val durationInMinutes: Int,
    val totalWorkingSetsDone: Int,
    val dateString: String,
    val day: String,
    val split: Int? = null,
)
