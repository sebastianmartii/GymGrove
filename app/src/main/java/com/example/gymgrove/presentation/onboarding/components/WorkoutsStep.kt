package com.example.gymgrove.presentation.onboarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R

internal class WorkoutsStep : OnBoardingStep {

    @Composable
    override fun Content() {
        OnBoardingInfoScreen(
            image = painterResource(R.drawable.dark_theme_workouts_onboarding_image),
            title = stringResource(id = R.string.workouts_title),
            message = stringResource(id = R.string.onboarding_workouts_step_message),
            imageSize = 220.dp
        )
    }
}