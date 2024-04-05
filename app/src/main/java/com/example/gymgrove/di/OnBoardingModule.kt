package com.example.gymgrove.di

import android.content.Context
import com.example.gymgrove.domain.onboarding.service.OnBoardingPreferences
import com.example.gymgrove.domain.onboarding.use_cases.CompleteOnBoarding
import com.example.gymgrove.domain.workout.service.WorkoutPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val ON_BOARDING_PREFERENCES_NAME = "on_boarding_preferences"

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {

    @Provides
    @Singleton
    fun provideCompleteOnBoardingUseCase(
        workoutPreferences: WorkoutPreferences,
        onBoardingPreferences: OnBoardingPreferences
    ): CompleteOnBoarding {
        return CompleteOnBoarding(workoutPreferences, onBoardingPreferences)
    }

    @Provides
    @Singleton
    fun provideOnBoardingPreferences(
        @ApplicationContext context: Context
    ): OnBoardingPreferences {
        return OnBoardingPreferences(
            context.getSharedPreferences(ON_BOARDING_PREFERENCES_NAME, Context.MODE_PRIVATE)
        )
    }
}