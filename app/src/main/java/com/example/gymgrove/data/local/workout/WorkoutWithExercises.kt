package com.example.gymgrove.data.local.workout

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gymgrove.data.local.exercise.Exercise

data class WorkoutWithExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val exercises: List<Exercise>
)
