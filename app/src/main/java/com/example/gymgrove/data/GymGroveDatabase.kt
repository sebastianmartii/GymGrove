package com.example.gymgrove.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gymgrove.data.exercise.local.Exercise
import com.example.gymgrove.data.exercise.local.ExerciseDao
import com.example.gymgrove.data.exercise.local.SavedExercise
import com.example.gymgrove.data.exercise.local.SavedExerciseDao
import com.example.gymgrove.data.notification.local.Notification
import com.example.gymgrove.data.notification.local.NotificationDao
import com.example.gymgrove.data.workout.local.SavedWorkout
import com.example.gymgrove.data.workout.local.SavedWorkoutDao
import com.example.gymgrove.data.workout.local.Workout
import com.example.gymgrove.data.workout.local.WorkoutDao
import com.example.gymgrove.data.workout.local.relations.SavedWorkoutSavedExerciseCrossRef

@Database(
    entities = [
        Workout::class,
        Exercise::class,
        Notification::class,
        SavedExercise::class,
        SavedWorkout::class,
        SavedWorkoutSavedExerciseCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GymGroveDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao

    abstract val exerciseDao: ExerciseDao

    abstract val savedExerciseDao: SavedExerciseDao

    abstract val savedWorkoutDao: SavedWorkoutDao

    abstract val notificationDao: NotificationDao
}