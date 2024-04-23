package com.example.gymgrove.presentation.history.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.gymgrove.R
import com.example.gymgrove.domain.workout.model.Workout
import com.example.gymgrove.presentation.history.HistoryScreenModel
import com.example.gymgrove.presentation.util.AppBar
import com.example.gymgrove.presentation.util.DashSeparator
import com.example.gymgrove.presentation.util.DotSeparator
import com.example.gymgrove.presentation.util.EmptyScreen
import com.example.gymgrove.presentation.util.LoadingScreen
import com.example.gymgrove.presentation.util.SearchTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HistoryScreen(
    state: HistoryScreenModel.State,
    onSearchQueryChange: (String?) -> Unit,
    onHistoryReset: () -> Unit,
    onWorkoutDelete: (Workout) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                titleContent = { Text(text = stringResource(id = R.string.history_title)) },
                searchQuery = state.searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                actions = persistentListOf(
                    AppBar.AppBarAction(
                        title = stringResource(id = R.string.reset_history_action),
                        icon = Icons.Default.Delete,
                        onClick = onHistoryReset
                    )
                )
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                LoadingScreen(modifier = Modifier.padding(paddingValues))
            }
            state.isHistoryEmpty -> {
                val msg = if (state.searchQuery == null) {
                    stringResource(id = R.string.empty_history_screen_message)
                } else {
                    stringResource(id = R.string.no_results_found_message)
                }
                EmptyScreen(
                    message = msg,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            else -> {
                HistoryScreenContent(
                    history = state.items,
                    modifier = Modifier.padding(paddingValues),
                    onDeleteWorkout = onWorkoutDelete,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun HistoryScreenContent(
    history: ImmutableList<Workout>,
    onDeleteWorkout: (Workout) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(history) { workout ->
            ListItem(
                headlineContent = {
                    Row {
                        Text(text = workout.name)
                        DashSeparator()
                        Text(text = workout.day)
                    }
                },
                supportingContent = {
                    Row {
                        Text(text = workout.date)
                        DotSeparator()
                        Text(text = stringResource(id = R.string.minutes_argument_text, workout.durationInMinutes))
                        DotSeparator()
                        Text(text = stringResource(id = R.string.sets_argument_text, workout.totalWorkingSets))
                    }
                },
                trailingContent = {
                    IconButton(onClick = {
                        onDeleteWorkout(workout)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.delete_workout_action)
                        )
                    }
                },
                modifier = Modifier.clickable {
                    onItemClick(workout.id)
                }
            )
        }
    }
}