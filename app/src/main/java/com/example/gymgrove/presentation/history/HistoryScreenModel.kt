package com.example.gymgrove.presentation.history

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gymgrove.domain.workout.model.Workout
import com.example.gymgrove.domain.workout.repository.WorkoutRepository
import com.example.gymgrove.domain.workout.use_cases.DeleteWorkout
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryScreenModel @Inject constructor(
    private val deleteWorkout: DeleteWorkout,
    private val workoutRepository: WorkoutRepository
) : StateScreenModel<HistoryScreenModel.State>(State()) {

    init {
        screenModelScope.launch {
            combine(
                workoutRepository.getAllWorkouts(),
                state.map { it.searchQuery }.distinctUntilChanged()
            ) { workouts, query ->
                if (query.isNullOrBlank()) {
                    workouts
                } else {
                    workouts.filter {
                        it.name.contains(query) || it.date.contains(query) || it.totalWorkingSets.toString().contains(query)
                                    || it.durationInMinutes.toString().contains(query) || it.day.contains(query)
                    }
                }
            }.collectLatest { items ->
                mutableState.update {
                    it.copy(
                        items = items.toImmutableList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun changeSearchQuery(query: String?) {
        mutableState.update {
            it.copy(
                searchQuery = query
            )
        }
    }

    fun resetHistory() {
        screenModelScope.launch {
            workoutRepository.resetHistory()
        }
    }

    fun deleteWorkout(workout: Workout, removeExercises: Boolean) {
        screenModelScope.launch {
            deleteWorkout.delete(workout, removeExercises)
        }
    }

    fun setDialog(dialog: Dialog?) {
        mutableState.update {
            it.copy(
                dialog = dialog
            )
        }
    }

    @Immutable
    data class State(
        val isLoading: Boolean = true,
        val dialog: Dialog? = null,
        val items: ImmutableList<Workout> = persistentListOf(),
        val searchQuery: String? = null,
    ) {
        val isHistoryEmpty by lazy {
            items.isEmpty()
        }
    }

    sealed interface Dialog {
        data object DeleteAll : Dialog
        data class Delete(val workout: Workout) : Dialog
    }
}