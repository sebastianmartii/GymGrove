package com.example.gymgrove.data.local.workout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workout: Workout)

    @Query("SELECT * FROM workouts WHERE dateString = :yesterday")
    fun getYesterdayWorkout(yesterday: String): Flow<Workout?>

    @Query("SELECT * FROM workouts WHERE dateString = :today")
    fun getTodayWorkout(today: String): Flow<Workout?>

    @Query("SELECT * FROM workouts WHERE dateString LIKE '%-' || :currentMonth || '-%'")
    suspend fun getCurrentMonthWorkouts(currentMonth: String): List<Workout>

    @Query("SELECT COUNT(id) FROM workouts WHERE dateString LIKE '%-' || :currentMonth || '-%'")
    fun getCurrentMonthWorkoutsCount(currentMonth: String): Flow<Int>

    @Transaction
    @Query("SELECT * FROM workouts WHERE dateString = :date")
    suspend fun getWorkoutWithExercises(date: String): WorkoutWithExercises
}