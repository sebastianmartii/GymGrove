package com.example.gymgrove.presentation.routine.exercises

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gymgrove.domain.exercise.model.SavedExercise
import com.example.gymgrove.domain.exercise.repository.ExerciseRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesScreenModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) : StateScreenModel<ExercisesScreenModel.State>(State()) {

    init {
        screenModelScope.launch {
            combine(
                exerciseRepository.getAllSavedExercisesAsFlow(),
                state.map { it.searchQuery }.distinctUntilChanged()
            ) { savedExercises, query ->
                if (query.isNullOrBlank()) {
                    savedExercises
                } else {
                    savedExercises.filter {
                        it.name.contains(query)
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

    fun selectOne(id: Int) {
        mutableState.update { state ->
            val newSelectedItemsIds = state.selectedItemsIds.mutate {
                if (it.contains(id)) {
                    it.remove(id)
                } else {
                    it.add(id)
                }
            }
            state.copy(selectedItemsIds = newSelectedItemsIds)
        }
    }

    fun delete(items: List<Int>) {
        screenModelScope.launch {
            exerciseRepository.delete(items)
        }
    }

    fun unselectAllItems() {
        mutableState.update {
            it.copy(
                selectedItemsIds = persistentListOf()
            )
        }
    }

    @Immutable
    data class State(
        val isLoading: Boolean = true,
        val searchQuery: String? = null,
        val items: ImmutableList<SavedExercise> = persistentListOf(),
        val selectedItemsIds: PersistentList<Int> = persistentListOf()
    ) {
        val isAnySelected by lazy {
            selectedItemsIds.isNotEmpty()
        }
        val isEmpty = items.isEmpty()
    }
}