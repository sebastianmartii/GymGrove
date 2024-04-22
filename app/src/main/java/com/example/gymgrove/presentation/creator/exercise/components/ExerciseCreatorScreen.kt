package com.example.gymgrove.presentation.creator.exercise.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R
import com.example.gymgrove.presentation.creator.exercise.ExerciseCreatorScreenModel
import com.example.gymgrove.presentation.util.AppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseCreatorScreen(
    state: ExerciseCreatorScreenModel.State,
    muscles: ImmutableList<Muscles>,
    onItemClick: (String?, Boolean, Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    onTipsChange: (String) -> Unit,
    onExerciseSave: (String, String, String?, String?) -> Unit,
    onNavigateBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            AppBar(
                title = "",
                onNavigateBack = onNavigateBack,
                actions = persistentListOf()
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = state.showSaveButton) {
                ExtendedFloatingActionButton(
                    onClick = {
                        onExerciseSave(state.name!!, state.primaryTargetMuscle!!, state.secondaryTargetMuscle, state.tips)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = stringResource(id = R.string.save_action)
                        )
                        Text(text = stringResource(id = R.string.save_action))
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            Section(
                title = stringResource(id = R.string.exercise_name_section_title),
                icon = Icons.Outlined.NoteAlt
            ) {
                OutlinedTextField(
                    value = state.name ?: "",
                    onValueChange = onNameChange,
                    label = {
                        Text(text = stringResource(id = R.string.name_label))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Section(
                title = stringResource(id = R.string.exercise_target_muscles_section_title),
                icon = Icons.Default.Bolt
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    muscles.map { it.toMuscleString() }.forEach { muscle ->
                        val isPrimary = muscle == state.primaryTargetMuscle
                        val isSecondary = muscle == state.secondaryTargetMuscle
                        val selected = isPrimary || isSecondary
                        val containerColor = if (isPrimary) {
                            MaterialTheme.colorScheme.secondaryContainer
                        } else {
                            MaterialTheme.colorScheme.tertiaryContainer
                        }
                        val itemsColor = if (isPrimary) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onTertiaryContainer
                        }
                        FilterChip(
                            selected = selected,
                            onClick = {
                                onItemClick(muscle, isPrimary, isSecondary)
                            },
                            label = {
                                Text(text = muscle)
                            },
                            leadingIcon = {
                                if (selected) {
                                    Icon(
                                        imageVector = Icons.Outlined.Done,
                                        contentDescription = stringResource(id = R.string.selected_muscle_chip)
                                    )
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors().copy(
                                selectedContainerColor = containerColor,
                                selectedLabelColor = itemsColor,
                                selectedLeadingIconColor = itemsColor,
                            )
                        )
                    }
                }
            }
            Section(
                title = stringResource(id = R.string.exercise_additional_info_section_title),
                icon = Icons.Default.Insights,
                showDivider = false
            ) {
                OutlinedTextField(
                    value = state.tips ?: "",
                    onValueChange = onTipsChange,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.additional_info_hint),
                            modifier = Modifier.alpha(.7f)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = R.string.additional_info_label))
                    },
                    supportingText = {
                        Text(text = stringResource(id = R.string.optional_supporting_text))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    minLines = 3,
                    maxLines = 6,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Section(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = title
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
        content()
        if (showDivider) {
            HorizontalDivider()
        }
    }
}