package com.example.gymgrove.presentation.onboarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.gymgrove.R

internal class HistoryStep : OnBoardingStep {

    @Composable
    override fun Content() {
        OnBoardingInfoScreen(
            image = painterResource(R.drawable.dark_theme_history_onboarding_image),
            title = stringResource(id = R.string.history_title),
            message = stringResource(id = R.string.onboarding_history_step_message)
        )
    }
}