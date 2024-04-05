package com.example.gymgrove.domain.exercise.model

data class SavedExercise(
    val id: Int,
    val name: String,
    val primaryTargetMuscle: String,
    // optional
    val secondaryTargetMuscle: String? = null,
    val additionalInfo: String? = null
)