package com.example.gymgrove.presentation.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.gymgrove.R
import com.example.gymgrove.presentation.util.AppBar
import com.example.gymgrove.presentation.util.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

object BackgroundsScreen : Screen() {
    private fun readResolve(): Any = BackgroundsScreen

    private val backgrounds: ImmutableList<Int> = persistentListOf(
        R.drawable.next_workout_box_background_1,
        R.drawable.next_workout_box_background_2,
        R.drawable.next_workout_box_background_3,
        R.drawable.next_workout_box_background_4,
        R.drawable.next_workout_box_background_5,
        R.drawable.next_workout_box_background_6,
        R.drawable.next_workout_box_background_7,
        R.drawable.next_workout_box_background_8,
        R.drawable.next_workout_box_background_9,
        R.drawable.next_workout_box_background_10,
        R.drawable.next_workout_box_background_11,
        R.drawable.next_workout_box_background_12,
        R.drawable.next_workout_box_background_13,
        R.drawable.next_workout_box_background_14,
        R.drawable.next_workout_box_background_15,
        R.drawable.next_workout_box_background_16,
        R.drawable.next_workout_box_background_17,
        R.drawable.next_workout_box_background_18,
        R.drawable.next_workout_box_background_19,
        R.drawable.next_workout_box_background_20,
        R.drawable.next_workout_box_background_21,
        R.drawable.next_workout_box_background_22,
        R.drawable.next_workout_box_background_23,
        R.drawable.next_workout_box_background_24
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<DashboardScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                AppBar(
                    title = stringResource(id = R.string.next_workout_box_change_background_action),
                    actions = persistentListOf(),
                    onNavigateBack = {
                        navigator.pop()
                    }
                )
            }
        ) { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 12.dp,
                        end = 12.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .fillMaxSize()
            ) {
                items(backgrounds) { background ->
                    val selected = state.background == background
                    AsyncImage(
                        model = background,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .border(
                                width = if (selected) 4.dp else 0.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                screenModel.changeBackground(background)
                            }
                    )
                }
            }
        }
    }
}