package com.example.gymgrove.presentation.creator.exercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.presentation.creator.exercise.components.LowerBody
import com.example.gymgrove.presentation.creator.exercise.components.UpperBody
import com.example.gymgrove.presentation.creator.exercise.components.ExerciseCreatorScreen
import com.example.gymgrove.presentation.util.Screen
import kotlinx.collections.immutable.persistentListOf

data class ExerciseCreatorScreen(
    val exerciseId: Int? = null
) : Screen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<ExerciseCreatorScreenModel, ExerciseCreatorScreenModel.Factory> { factory ->
            factory.create(exerciseId)
        }
        val state by screenModel.state.collectAsStateWithLifecycle()

        ExerciseCreatorScreen(
            state = state,
            muscles = persistentListOf(
                UpperBody.Chest,
                UpperBody.Triceps,
                UpperBody.Shoulders,
                UpperBody.Biceps,
                UpperBody.UpperBack,
                UpperBody.Lats,
                UpperBody.Forearms,
                LowerBody.Quads,
                LowerBody.Hamstrings,
                LowerBody.Glutes,
                LowerBody.Calves
            ),
            onItemClick = screenModel::selectTargetMuscle,
            onNameChange = screenModel::changeName,
            onTipsChange = screenModel::changeTips,
            onExerciseSave = screenModel::saveExercise,
            onNavigateBack = navigator::pop
        )
    }
}