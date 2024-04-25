package com.example.gymgrove.di

import com.example.gymgrove.domain.exercise.repository.ExerciseRepository
import com.example.gymgrove.domain.exercise.use_cases.GetBigThreeLifts
import com.example.gymgrove.domain.exercise.use_cases.UpdateSavedExercise
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExerciseModule {

    @Provides
    @Singleton
    fun provideUpdateSavedExerciseUseCase(
        exercisesRepository: ExerciseRepository
    ): UpdateSavedExercise {
        return UpdateSavedExercise(exercisesRepository)
    }

    @Provides
    @Singleton
    fun provideGetBigThreeLiftsUseCase(
        exercisesRepository: ExerciseRepository
    ): GetBigThreeLifts {
        return GetBigThreeLifts(exercisesRepository)
    }
}