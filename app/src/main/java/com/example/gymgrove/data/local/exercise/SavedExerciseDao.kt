package com.example.gymgrove.data.local.exercise

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedExerciseDao {

    @Insert
    suspend fun insert(savedExercise: SavedExercise)

    @Delete
    suspend fun delete(savedExercise: SavedExercise)

    @Transaction
    suspend fun deleteAll(savedExercises: List<SavedExercise>) {
        savedExercises.onEach { savedExercise ->
            delete(savedExercise)
        }
    }

    @Query("SELECT * FROM saved_exercises")
    fun getSavedExercises(): Flow<List<SavedExercise>>
}