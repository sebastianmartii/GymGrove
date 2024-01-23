package com.example.gymgrove.data.local.exercise

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

    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :exerciseName || '%'")
    fun getExerciseHistory(exerciseName: String): Flow<List<Exercise>>
}