package com.example.gymgrove.presentation.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.HistoryToggleOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.gymgrove.R
import com.example.gymgrove.presentation.details.workout.WorkoutDetailsScreen
import com.example.gymgrove.presentation.history.components.HistoryDeleteAllDialog
import com.example.gymgrove.presentation.history.components.HistoryDeleteDialog
import com.example.gymgrove.presentation.history.components.HistoryScreen

object HistoryTab : Tab {

    private fun readResolve(): Any = HistoryTab

    override val options: TabOptions
        @Composable
        get() {
            val isSelected = LocalTabNavigator.current.current.key == key
            val icon = if (isSelected) Icons.Filled.History else Icons.Outlined.HistoryToggleOff
            return TabOptions(
                index = 2u,
                title = stringResource(id = R.string.history_title),
                icon = rememberVectorPainter(image = icon)
            )
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HistoryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.currentOrThrow

        HistoryScreen(
            state = state,
            onSearchQueryChange = screenModel::changeSearchQuery,
            onHistoryReset = {
                screenModel.setDialog(HistoryScreenModel.Dialog.DeleteAll)
            },
            onWorkoutDelete = { workout ->
                screenModel.setDialog(HistoryScreenModel.Dialog.Delete(workout))
            },
            onItemClick = { id ->
                navigator.push(WorkoutDetailsScreen(id))
            }
        )

        val onDismissRequest = { screenModel.setDialog(null) }
        when(val dialog = state.dialog) {
            is HistoryScreenModel.Dialog.Delete -> {
                HistoryDeleteDialog(
                    onDismissRequest = onDismissRequest,
                    onDelete = { removeExercises ->
                        screenModel.deleteWorkout(dialog.workout, removeExercises)
                    }
                )
            }
            HistoryScreenModel.Dialog.DeleteAll -> {
                HistoryDeleteAllDialog(
                    onDismissRequest = onDismissRequest,
                    onDeleteAll = screenModel::resetHistory
                )
            }
            null -> {}
        }
    }
}