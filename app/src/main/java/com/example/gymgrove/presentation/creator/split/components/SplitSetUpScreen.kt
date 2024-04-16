package com.example.gymgrove.presentation.creator.split.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R
import com.example.gymgrove.domain.onboarding.model.Split
import com.example.gymgrove.presentation.creator.split.SplitSetUpScreenModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitSetUpScreen(
    popularSplits: ImmutableList<Split>,
    state: SplitSetUpScreenModel.State,
    onInputFieldChange: (String) -> Unit,
    onWorkoutAdd: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onComplete: (PersistentList<String>) -> Unit,
    onUndo: () -> Unit
) {
   Scaffold(
       topBar = {
           TopAppBar(
               title = {
                    Text(text = stringResource(id = R.string.split_set_up_screen_title))
               },
               navigationIcon = {
                   IconButton(onClick = onNavigateBack) {
                       Icon(
                           imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                           contentDescription = stringResource(id = R.string.navigate_back_action)
                       )
                   }
               },
               actions = {
                   TextButton(
                       onClick = onUndo,
                       enabled = state.isSplitNotEmpty
                   ) {
                       Text(text = stringResource(id = R.string.undo_action))
                   }
                   TextButton(
                       onClick = {
                           onComplete(state.workouts)
                       },
                       enabled = state.isSplitNotEmpty
                   ) {
                       Text(text = stringResource(id = R.string.finish))
                   }
               }
           )
       }
   ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item(
                key = SplitSetUpScreenItems.POPULAR_SPLITS_ROW,
                contentType = SplitSetUpScreenItems.POPULAR_SPLITS_ROW
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp)
                ) {
                    popularSplits.forEach { split ->
                        AssistChip(
                            onClick = {
                                onComplete(split.splitWorkouts)
                            },
                            label = {
                                Text(text = split.splitName)
                            }
                        )
                    }
                }
            }
            itemsIndexed(
                items = state.workouts,
                key = { index, workout ->
                    "${SplitSetUpScreenItems.WORKOUT}$workout$index"
                },
                contentType = { _, _ ->
                    SplitSetUpScreenItems.WORKOUT
                }
            ) { index, workout ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = workout,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    },
                    supportingContent = {
                        Text(
                            text = workoutSupportingText(index),
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    tonalElevation = 4.dp,
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                        .clip(MaterialTheme.shapes.medium)
                        .height(80.dp)
                )
            }
            item(
                key = SplitSetUpScreenItems.INPUT_FIELD,
                contentType = SplitSetUpScreenItems.INPUT_FIELD
            ) {
                OutlinedTextField(
                    value = state.inputField,
                    onValueChange = onInputFieldChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onWorkoutAdd(state.inputField)
                        }
                    ),
                    label = {
                        Text(text = stringResource(id = R.string.add_split_workout_action))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.push_workout),
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier
                                .alpha(.7f)
                        )
                    }
                )
            }
        }
   }
}

private enum class SplitSetUpScreenItems {
    WORKOUT, INPUT_FIELD, POPULAR_SPLITS_ROW
}

private fun workoutSupportingText(index: Int): String {
    val order = when(index + 1) {
        1 -> "First"
        2 -> "Second"
        3 -> "Third"
        4 -> "Fourth"
        5 -> "Fifth"
        6 -> "Sixth"
        7 -> "Seventh"
        8 -> "Eighth"
        9 -> "Ninth"
        else -> "${index + 1}th"
    }
    return "$order Workout"
}