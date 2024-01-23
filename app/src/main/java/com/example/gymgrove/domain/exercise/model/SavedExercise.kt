package com.example.gymgrove.domain.exercise.model

data class SavedExercise(
    val id: Int,
    val name: String,
    val primaryTargetMuscle: TargetMuscles,
    // optional
    val secondaryTargetMuscle: TargetMuscles? = null,
    val tips: String? = null
)
