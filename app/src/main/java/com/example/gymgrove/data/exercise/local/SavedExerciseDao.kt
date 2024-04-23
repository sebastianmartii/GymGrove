package com.example.gymgrove.data.exercise.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedExerciseDao {

    @Insert
    suspend fun insert(savedExercise: SavedExercise)

    @Query("DELETE FROM saved_exercises WHERE savedExerciseId = :id")
    suspend fun delete(id: Int)

    @Transaction
    suspend fun deleteAll(ids: List<Int>) {
        ids.onEach { id ->
            delete(id)
        }
    }

    @Query("SELECT * FROM saved_exercises WHERE savedExerciseId = :id")
    suspend fun getSavedExercise(id: Int): SavedExercise

    @Query("SELECT * FROM saved_exercises")
    fun getSavedExercises(): Flow<List<SavedExercise>>

    @Query("SELECT * FROM saved_exercises")
    suspend fun getSavedExercisesAsList(): List<SavedExercise>

    @Transaction
    suspend fun getSavedExercisesAsListByIds(exercisesIds: List<Int>): List<SavedExercise> {
        val exercises: MutableList<SavedExercise> = mutableListOf()
        exercisesIds.forEach { id ->
            exercises.add(getSavedExercise(id))
        }
        return exercises
    }

    @Query("UPDATE saved_exercises SET savedExerciseName = :name WHERE savedExerciseId = :id")
    suspend fun updateName(name: String, id: Int)

    @Query("UPDATE saved_exercises SET primaryTargetMuscle = :primaryTargetMuscle WHERE savedExerciseId = :id")
    suspend fun updatePrimaryTargetMuscle(primaryTargetMuscle: String, id: Int)

    @Query("UPDATE saved_exercises SET secondaryTargetMuscle = :secondaryTargetMuscle WHERE savedExerciseId = :id")
    suspend fun updateSecondaryTargetMuscle(secondaryTargetMuscle: String, id: Int)

    @Query("UPDATE saved_exercises SET additionalInfo = :tips WHERE savedExerciseId = :id")
    suspend fun updateTips(tips: String, id: Int)
}