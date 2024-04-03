package com.example.gymgrove.data.exercise.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_exercises")
data class SavedExercise(
    @PrimaryKey(autoGenerate = true)
    val savedExerciseId: Int? = null,
    val savedExerciseName: String,
    val primaryTargetMuscle: String,
    // optional
    val secondaryTargetMuscle: String? = null,
    val additionalInfo: String? = null
)
