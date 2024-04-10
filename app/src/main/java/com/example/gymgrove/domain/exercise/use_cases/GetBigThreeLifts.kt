package com.example.gymgrove.domain.exercise.use_cases

import com.example.gymgrove.domain.exercise.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetBigThreeLifts(
    private val repo: ExerciseRepository
) {
    fun subscribe(): Flow<Triple<Float, Float, Float>> = combine(
        repo.getBenchPressMaxWeight(),
        repo.getSquatMaxWeight(),
        repo.getDeadliftMaxWeight()
    ) { benchPress, squat, deadlift ->
        Triple(benchPress, squat, deadlift)
    }
}