package com.example.gymgrove.domain.workout.model

data class Workout(
    val id: Int,
    val name: String,
    val durationInMinutes: Int,
    val totalWorkingSets: Int,
    val day: String,
    val date: String,
    val split: Int? = null,
)
