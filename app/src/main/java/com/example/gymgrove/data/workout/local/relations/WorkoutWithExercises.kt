package com.example.gymgrove.data.workout.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gymgrove.data.exercise.local.Exercise
import com.example.gymgrove.data.workout.local.Workout

data class WorkoutWithExercises(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val exercises: List<Exercise>
)
