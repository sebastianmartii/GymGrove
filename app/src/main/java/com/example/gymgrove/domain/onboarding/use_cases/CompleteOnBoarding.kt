package com.example.gymgrove.domain.onboarding.use_cases

import com.example.gymgrove.domain.onboarding.service.OnBoardingPreferences
import com.example.gymgrove.domain.workout.service.WorkoutPreferences
import kotlinx.collections.immutable.PersistentList

class CompleteOnBoarding(
    private val workoutPreferences: WorkoutPreferences,
    private val onBoardingPreferences: OnBoardingPreferences
) {
    suspend fun complete(workouts: PersistentList<String>) {
        onBoardingPreferences.saveIsOnBoardingCompleted(true)
        workoutPreferences.saveSplit(workouts)
    }
}