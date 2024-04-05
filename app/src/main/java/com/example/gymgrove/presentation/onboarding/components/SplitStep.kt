package com.example.gymgrove.presentation.onboarding.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R
import com.example.gymgrove.domain.onboarding.model.Split
import kotlinx.collections.immutable.persistentListOf

internal class SplitStep(
    private val selectedSplit: Split?,
    private val onSplitSelect: (Split) -> Unit,
    private val onNavigateToSplitSetUpScreen: () -> Unit
) : OnBoardingStep {

    private val popularSplits = persistentListOf(
        Split(
            splitName = "PPL",
            splitWorkouts = persistentListOf(
                "Push",
                "Pull",
                "Legs"
            )
        ),
        Split(
            splitName = "Upper/Lower",
            splitWorkouts = persistentListOf(
                "Upper",
                "Lower"
            )
        ),
        Split(
            splitName = "Full Body",
            splitWorkouts = persistentListOf(
                "Full Body Workout",
                "Rest"
            )
        ),
    )

    @Composable
    override fun Content() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_split_step_header),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))
            popularSplits.forEach { split ->
                val selected = selectedSplit == split
                Card(
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = if (!selected)
                            MaterialTheme.colorScheme.surfaceContainerHighest
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (selected) 6.dp else 2.dp,
                    ),
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            onSplitSelect(split)
                        }
                ) {
                    Text(
                        text = split.splitName,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                            )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = split.splitWorkouts.joinToString(separator = " - "),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = onNavigateToSplitSetUpScreen,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.navigate_to_split_set_up_action))
            }
        }
    }
}