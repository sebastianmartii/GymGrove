package com.example.gymgrove.data.exercise.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insert(exercise: Exercise)

    @Transaction
    suspend fun insertAll(exercises: List<Exercise>) {
        exercises.onEach { exercise ->
            insert(exercise)
        }
    }

    @Query("DELETE FROM exercises WHERE workoutId = :id")
    suspend fun deleteWorkoutExercises(id: Int)

    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :exerciseName || '%'")
    fun getExerciseHistory(exerciseName: String): Flow<List<Exercise>>

    @Query("SELECT MAX(workingWeight) FROM exercises WHERE name LIKE '%' || :exerciseName || '%'")
    fun getExerciseMaxWeight(exerciseName: String): Flow<Float>
}