package com.example.gymgrove.data.exercise.repository

import com.example.gymgrove.data.exercise.local.ExerciseDao
import com.example.gymgrove.data.exercise.local.SavedExerciseDao
import com.example.gymgrove.data.exercise.toSavedExerciseModel
import com.example.gymgrove.domain.exercise.model.SavedExercise
import com.example.gymgrove.domain.exercise.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.gymgrove.data.exercise.local.SavedExercise as SavedExerciseEntity
import com.example.gymgrove.domain.exercise.model.SavedExercise as SavedExerciseModel

private const val BENCH_PRESS = "bench press"
private const val SQUAT = "squat"
private const val DEADLIFT = "deadlift"

class ExerciseRepositoryImpl @Inject constructor(
    private val savedExerciseDao: SavedExerciseDao,
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {

    override suspend fun getSavedExercisesAsListByIds(exercisesIds: List<Int>): List<SavedExercise> {
        return savedExerciseDao.getSavedExercisesAsListByIds(exercisesIds).map { it.toSavedExerciseModel() }
    }

    override fun getAllSavedExercisesAsFlow(): Flow<List<SavedExerciseModel>> {
        return savedExerciseDao.getSavedExercises().map {
            it.map { savedExercise -> savedExercise.toSavedExerciseModel() }
        }
    }

    override suspend fun getAllSavedExercises(): List<SavedExerciseModel> {
        return savedExerciseDao.getSavedExercisesAsList().map { it.toSavedExerciseModel() }
    }

    override suspend fun insert(
        name: String,
        primaryTargetMuscle: String,
        secondaryTargetMuscle: String?,
        tips: String?
    ) {
        savedExerciseDao.insert(
            SavedExerciseEntity(
                savedExerciseName = name,
                primaryTargetMuscle = primaryTargetMuscle,
                secondaryTargetMuscle = secondaryTargetMuscle,
                additionalInfo = tips
            )
        )
    }

    override suspend fun delete(items: List<Int>) {
        savedExerciseDao.deleteAll(items)
    }

    override suspend fun updateName(name: String, id: Int) {
        savedExerciseDao.updateName(name, id)
    }

    override suspend fun updatePrimaryTargetMuscle(primaryTargetMuscle: String, id: Int) {
        savedExerciseDao.updatePrimaryTargetMuscle(primaryTargetMuscle, id)
    }

    override suspend fun updateSecondaryTargetMuscle(secondaryTargetMuscle: String, id: Int) {
        savedExerciseDao.updateSecondaryTargetMuscle(secondaryTargetMuscle, id)
    }

    override suspend fun updateTips(tips: String, id: Int) {
        savedExerciseDao.updateTips(tips, id)
    }

    override suspend fun getSavedExercise(id: Int): SavedExercise {
        return savedExerciseDao.getSavedExercise(id).toSavedExerciseModel()
    }

    override fun getBenchPressMaxWeight(): Flow<Float> {
        return exerciseDao.getExerciseMaxWeight(BENCH_PRESS)
    }

    override fun getSquatMaxWeight(): Flow<Float> {
        return exerciseDao.getExerciseMaxWeight(SQUAT)
    }

    override fun getDeadliftMaxWeight(): Flow<Float> {
        return exerciseDao.getExerciseMaxWeight(DEADLIFT)
    }

    override suspend fun deleteWorkoutExercises(id: Int) {
        exerciseDao.deleteWorkoutExercises(id)
    }
}