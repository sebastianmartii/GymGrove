package com.example.gymgrove.presentation.routine.exercises

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.R
import com.example.gymgrove.presentation.creator.exercise.ExerciseCreatorScreen
import com.example.gymgrove.presentation.routine.TabContent
import com.example.gymgrove.presentation.util.AppBar
import com.example.gymgrove.presentation.util.EmptyScreen
import com.example.gymgrove.presentation.util.LoadingScreen
import kotlinx.collections.immutable.persistentListOf

@Composable
fun exercisesTab(
    exercisesScreenModel: ExercisesScreenModel
): TabContent {
    val navigator = LocalNavigator.currentOrThrow
    val state by exercisesScreenModel.state.collectAsStateWithLifecycle()

    return TabContent(
        title = stringResource(id = R.string.exercises_title),
        searchQuery = state.searchQuery,
        onSearchQueryChange = exercisesScreenModel::changeSearchQuery,
        content = {
            when {
                state.isLoading -> {
                    LoadingScreen()
                }
                state.isEmpty -> {
                    EmptyScreen(message = stringResource(id = R.string.empty_saved_exercises_screen_message))
                }
                else -> {
                    ExercisesTabContent(
                        state = state,
                        onExerciseSelect = exercisesScreenModel::selectOne
                    )
                }
            }
        },
        actions = persistentListOf(
            AppBar.AppBarAction(
                title = stringResource(id = R.string.navigate_to_exercise_creator_action),
                icon = Icons.Default.Add,
                onClick = {
                    navigator.push(ExerciseCreatorScreen(null))
                }
            )
        )
    )
}

@Composable
private fun ExercisesTabContent(
    state: ExercisesScreenModel.State,
    onExerciseSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(
            items = state.items,
            key = {
                it.id
            }
        ) { savedExercise ->
            val isSelected = state.selectedItemsIds.fastAny { it == savedExercise.id }
            ListItem(
                headlineContent = {
                    Text(text = savedExercise.name)
                },
                supportingContent = {
                    val secondaryTargetMuscle = if (savedExercise.secondaryTargetMuscle == null) {
                        ""
                    } else {
                        ", ${savedExercise.secondaryTargetMuscle}"
                    }
                    Text(text = savedExercise.primaryTargetMuscle + secondaryTargetMuscle)
                },
                tonalElevation = if (isSelected) 4.dp else 0.dp,
                modifier = Modifier.clickable {
                    onExerciseSelect(savedExercise.id)
                }
            )
        }
    }
}