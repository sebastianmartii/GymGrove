package com.example.gymgrove.data.local.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_exercises")
data class SavedExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val primaryTargetMuscle: String,
    // optional
    val secondaryTargetMuscle: String? = null,
    val tips: String? = null
)
