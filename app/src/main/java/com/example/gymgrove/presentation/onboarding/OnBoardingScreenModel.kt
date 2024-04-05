package com.example.gymgrove.presentation.onboarding

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gymgrove.domain.onboarding.model.Split
import com.example.gymgrove.domain.onboarding.use_cases.CompleteOnBoarding
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardingScreenModel @Inject constructor(
    private val completeOnBoarding: CompleteOnBoarding
) : StateScreenModel<OnBoardingScreenModel.State>(State()) {

    fun changeSplit(split: Split) {
        mutableState.update {
            val newSplit = if (it.selectedSplit == split) null else split
            it.copy(
                selectedSplit = newSplit
            )
        }
    }

    fun completeOnBoarding(workouts: PersistentList<String>) {
        screenModelScope.launch {
            completeOnBoarding.complete(workouts)
        }
    }

    fun closeDialog() {
        mutableState.update {
            it.copy(
                isPermissionDialogVisible = false
            )
        }
    }

    fun showDialog() {
        mutableState.update {
            it.copy(
                isPermissionDialogVisible = true
            )
        }
    }

    @Immutable
    data class State(
        val selectedSplit: Split? = null,
        val isPermissionDialogVisible: Boolean = false
    )
}