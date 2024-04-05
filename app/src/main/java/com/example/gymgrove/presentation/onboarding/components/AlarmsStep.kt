package com.example.gymgrove.presentation.onboarding.components

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R

internal class AlarmsStep(
    private val onScheduleExactAlarmsPermissionRequest: () -> Unit = {}
) : OnBoardingStep {

    @Composable
    override fun Content() {
        Column {
            OnBoardingInfoScreen(
                image = painterResource(R.drawable.dark_theme_alarms_onboarding_image),
                title = stringResource(id = R.string.alarms_title),
                message = stringResource(id = R.string.onboarding_alarms_step_message),
                modifier = Modifier.weight(.93f)
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Button(
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = onScheduleExactAlarmsPermissionRequest,
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .weight(.07f)

                ) {
                    Text(text = stringResource(id = R.string.allow_action))
                }
            }
        }
    }
}