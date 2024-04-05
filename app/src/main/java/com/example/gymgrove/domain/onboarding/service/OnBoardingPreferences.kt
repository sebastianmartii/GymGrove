package com.example.gymgrove.domain.onboarding.service

import android.content.SharedPreferences

private const val IS_ON_BOARDING_COMPLETED = "is_on_boarding_completed"

class OnBoardingPreferences(
    private val sharedPreferences: SharedPreferences
) {

    fun getIsOnBoardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(IS_ON_BOARDING_COMPLETED, false)
    }

    fun saveIsOnBoardingCompleted(isCompleted: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(IS_ON_BOARDING_COMPLETED, isCompleted)
            apply()
        }
    }
}