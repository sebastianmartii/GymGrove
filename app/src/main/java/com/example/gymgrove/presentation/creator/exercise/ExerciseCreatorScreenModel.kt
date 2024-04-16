package com.example.gymgrove.presentation.creator.exercise

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.example.gymgrove.domain.exercise.repository.ExerciseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ExerciseCreatorScreenModel @AssistedInject constructor(
    @Assisted val exerciseId: Int? = null,
    private val exerciseRepository: ExerciseRepository
) : StateScreenModel<ExerciseCreatorScreenModel.State>(State()) {


    init {
        screenModelScope.launch {
            if (exerciseId != null) {
                mutableState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                exerciseRepository.getSavedExercise(exerciseId).apply {
                    mutableState.update {
                        it.copy(
                            name = name,
                            primaryTargetMuscle = primaryTargetMuscle,
                            secondaryTargetMuscle = secondaryTargetMuscle,
                            tips = additionalInfo,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun changeTips(tips: String) {
        mutableState.update {
            it.copy(
                tips = tips
            )
        }
    }

    fun changeName(name: String) {
        mutableState.update {
            it.copy(
                name = name
            )
        }
    }

    fun selectTargetMuscle(targetMuscle: String?, isPrimary: Boolean, isSecondary: Boolean) {
        mutableState.update { state ->
            val primarySelected = state.primaryTargetMuscle != null
            val (primaryMuscle, secondaryMuscle) = when {
                isPrimary -> Pair(state.secondaryTargetMuscle, null) // 5
                isSecondary -> Pair(state.primaryTargetMuscle, null) // 4
                primarySelected -> Pair(state.primaryTargetMuscle, targetMuscle) // 2,3
                !primarySelected -> Pair(targetMuscle, null) // 1
                else -> Pair(null, null)
            }
            // 1 - if none are selected upon click muscle is selected as primary
            // 2 - if primary muscle is selected and secondary is not upon click muscle is selected as secondary
            // 3 - if both are selected when user clicks other muscle it is selected as secondary
            // 4 - if both are selected and user click secondary muscle it is unselected
            // 5 - if both are selected and user click primary muscle it is unselected and secondary becomes primary
            state.copy(
                primaryTargetMuscle = primaryMuscle,
                secondaryTargetMuscle = secondaryMuscle
            )
        }
    }

    fun saveExercise(name: String, primaryMuscle: String, secondaryMuscle: String?, tips: String?) {
        screenModelScope.launch {
            exerciseRepository.insert(name, primaryMuscle, secondaryMuscle, tips)
        }
    }

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val name: String? = null,
        val primaryTargetMuscle: String? = null,
        val secondaryTargetMuscle: String? = null,
        val tips: String? = null,
    ) {
        val showSaveButton = !name.isNullOrBlank() && primaryTargetMuscle != null
    }

    @AssistedFactory
    interface Factory : ScreenModelFactory {
        fun create(exerciseId: Int?): ExerciseCreatorScreenModel
    }
}