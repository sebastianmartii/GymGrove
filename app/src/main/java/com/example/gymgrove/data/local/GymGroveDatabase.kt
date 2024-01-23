package com.example.gymgrove.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gymgrove.data.local.exercise.Exercise
import com.example.gymgrove.data.local.exercise.ExerciseDao
import com.example.gymgrove.data.local.exercise.SavedExercise
import com.example.gymgrove.data.local.exercise.SavedExerciseDao
import com.example.gymgrove.data.local.workout.Workout
import com.example.gymgrove.data.local.workout.WorkoutDao

@Database(
    entities = [
        Workout::class,
        Exercise::class,
        SavedExercise::class
    ],
    version = 1
)
abstract class GymGroveDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao

    abstract val exerciseDao: ExerciseDao

    abstract val savedExerciseDao: SavedExerciseDao
}