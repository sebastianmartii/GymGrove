package com.example.gymgrove.domain.exercise.repository

import com.example.gymgrove.domain.exercise.model.SavedExercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun getSavedExercisesAsListByIds(exercisesIds: List<Int>): List<SavedExercise>

    fun getAllSavedExercisesAsFlow(): Flow<List<SavedExercise>>

    suspend fun getAllSavedExercises(): List<SavedExercise>

    suspend fun insert(name: String, primaryTargetMuscle: String, secondaryTargetMuscle: String?, tips: String?)

    suspend fun delete(items: List<Int>)

    suspend fun deleteWorkoutExercises(id: Int)

    suspend fun updateName(name: String, id: Int)

    suspend fun updatePrimaryTargetMuscle(primaryTargetMuscle: String, id: Int)

    suspend fun updateSecondaryTargetMuscle(secondaryTargetMuscle: String, id: Int)

    suspend fun updateTips(tips: String, id: Int)

    suspend fun getSavedExercise(id: Int): SavedExercise

    fun getBenchPressMaxWeight(): Flow<Float>

    fun getSquatMaxWeight(): Flow<Float>

    fun getDeadliftMaxWeight(): Flow<Float>
}