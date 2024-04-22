package com.example.gymgrove.presentation.creator.split

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.domain.onboarding.model.Split
import com.example.gymgrove.presentation.creator.split.components.SplitSetUpScreen
import com.example.gymgrove.presentation.util.NavigationHelper
import com.example.gymgrove.presentation.util.Screen
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest

object SplitSetUpScreen : Screen() {

    private fun readResolve(): Any = SplitSetUpScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<SplitSetUpScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(screenModel.navigationFlow) {
            screenModel.navigationFlow.collectLatest {
                when(it) {
                    NavigationHelper.UiEvent.NavigateBack -> {
                        navigator.pop()
                    }
                }
            }
        }

        SplitSetUpScreen(
            popularSplits = persistentListOf(
                Split(
                    splitName = "PPL",
                    splitWorkouts = persistentListOf(
                        "Push",
                        "Pull",
                        "Legs",
                    )
                ),
                Split(
                    splitName = "Upper/Lower",
                    splitWorkouts = persistentListOf(
                        "Upper Body",
                        "Lower Body"
                    )
                ),
                Split(
                    splitName = "Fullbody",
                    splitWorkouts = persistentListOf(
                        "Full Body Workout"
                    )
                ),
                Split(
                    splitName = "Bro Split",
                    splitWorkouts = persistentListOf(
                        "Chest",
                        "Back",
                        "Legs",
                        "Shoulders",
                        "Arms",
                    )
                ),
            ),
            state = state,
            onInputFieldChange = screenModel::changeInputField,
            onWorkoutAdd = screenModel::addWorkout,
            onNavigateBack = {
                navigator.pop()
            },
            onComplete = screenModel::completeSplit,
            onUndo = screenModel::undo
        )
    }
}