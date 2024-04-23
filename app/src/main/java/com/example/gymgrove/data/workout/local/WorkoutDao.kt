package com.example.gymgrove.data.workout.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.gymgrove.data.workout.local.relations.WorkoutWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insert(workout: Workout)

    @Query("DELETE FROM workouts")
    suspend fun resetHistory()

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workouts ORDER BY dateString DESC LIMIT 1")
    fun getLastWorkout(): Flow<Workout?>

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE dateString LIKE '%-' || :currentMonth || '-%'")
    suspend fun getCurrentMonthWorkouts(currentMonth: String): List<Workout>

    @Query("SELECT * FROM workouts WHERE split = (SELECT MAX(split) FROM workouts)")
    fun getCurrentSplit(): Flow<List<Workout>>

    @Query("SELECT COUNT(id) FROM workouts WHERE dateString LIKE '%-' || :currentMonth || '-%'")
    fun getCurrentMonthWorkoutsCount(currentMonth: String): Flow<Int>

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getWorkoutWithExercises(id: Int): WorkoutWithExercises

    @Query("SELECT SUM(durationInMinutes) FROM workouts WHERE dateString LIKE '%-' || :currentMonth || '-%'")
    fun getCurrentMonthTotalWorkoutsDuration(currentMonth: String): Flow<Int>
}