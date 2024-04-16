package com.example.gymgrove.presentation.creator.split

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gymgrove.domain.workout.service.WorkoutPreferences
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplitSetUpScreenModel @Inject constructor(
    private val workoutPreferences: WorkoutPreferences
) : StateScreenModel<SplitSetUpScreenModel.State>(State()) {

    private val navigationChannel = Channel<UiEvent>()
    val navigationFlow = navigationChannel.receiveAsFlow()

    fun changeInputField(input: String) {
        mutableState.update {
            it.copy(
                inputField = input
            )
        }
    }

    fun addWorkout(name: String) {
        val workoutName = if (name.isBlank() || name.isEmpty()) "Push" else name.replaceFirstChar {
            it.uppercase()
        }
        mutableState.update { state ->
            val newWorkouts = state.workouts.mutate {
                it.add(workoutName)
            }
            state.copy(
                workouts = newWorkouts,
                inputField = ""
            )
        }
    }

    fun completeSplit(workouts: PersistentList<String>) {
        screenModelScope.launch {
            workoutPreferences.saveSplit(workouts)
            navigationChannel.send(UiEvent.NavigateBack)
        }
    }

    fun undo() {
        mutableState.update { state ->
            val newWorkouts = state.workouts.mutate {
                it.removeLast()
            }
            state.copy(
                workouts = newWorkouts
            )
        }
    }

    @Immutable
    data class State(
        val workouts: PersistentList<String> = persistentListOf(),
        val inputField: String = "",
    ) {
        val isSplitNotEmpty = workouts.isNotEmpty()
    }

    sealed interface UiEvent {
        data object NavigateBack : UiEvent
    }
}