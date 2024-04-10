package com.example.gymgrove.presentation.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gymgrove.R
import com.example.gymgrove.domain.exercise.use_cases.GetBigThreeLifts
import com.example.gymgrove.domain.notification.model.Notification
import com.example.gymgrove.domain.notification.repository.NotificationRepository
import com.example.gymgrove.domain.workout.model.CurrentMonth
import com.example.gymgrove.domain.workout.model.Workout
import com.example.gymgrove.domain.workout.repository.WorkoutRepository
import com.example.gymgrove.domain.workout.service.WorkoutPreferences
import com.example.gymgrove.domain.workout.use_cases.GetCurrentMonth
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class DashboardScreenModel @Inject constructor(
    private val getBigThreeLifts: GetBigThreeLifts,
    private val getCurrentMonth: GetCurrentMonth,
    private val workoutPreferences: WorkoutPreferences,
    private val notificationRepository: NotificationRepository,
    private val workoutRepository: WorkoutRepository
) : StateScreenModel<DashboardScreenModel.State>(State()) {

    init {
        screenModelScope.launch {
            combine(
                workoutPreferences.splitWorkouts,
                workoutPreferences.nextWorkout,
                workoutRepository.getCurrentSplit()
            ) { split, nextWorkout, workouts ->
                RecentWorkouts(
                    split = split.associateToCurrentSplit(workouts),
                    nextWorkout = nextWorkout
                )
            }.collectLatest {
                mutableState.update { state ->
                    state.copy(
                        currentSplit = it.split,
                        nextWorkout = it.nextWorkout
                    )
                }
            }
        }
        screenModelScope.launch {
            combine(
                getBigThreeLifts.subscribe(),
                getCurrentMonth.subscribe()
            ) { bigThreeLifts, currentMonth ->
                mutableState.update {
                    it.copy(
                        bigThreeLifts = bigThreeLifts,
                        currentMonth = currentMonth
                    )
                }
            }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), State())
        }
        screenModelScope.launch {
            notificationRepository.getNotifications().collectLatest { notifications ->
                mutableState.update { state ->
                    val newNotifications = notifications.toMutableList()
                    newNotifications.add(
                        Notification(
                            id = 0,
                            title = "Schedule Notifications",
                            message = "",
                            hour = 25,
                            minute = 61,
                            repeating = false
                            )
                        )
                    newNotifications.sortedBy {
                            it.hour.toFloat() + (it.minute.toFloat()/100)
                        }
                    state.copy(
                        pendingNotifications = newNotifications.toPersistentList()
                    )
                }
            }
        }
    }

    fun expandMoreMenu() {
        mutableState.update {
            it.copy(
                moreMenuExpanded = true
            )
        }
    }

    fun collapseMoreMenu() {
        mutableState.update {
            it.copy(
                moreMenuExpanded = false
            )
        }
    }

    fun changeBackground(newBackground: Int) {
        mutableState.update {
            it.copy(
                background = newBackground
            )
        }
    }

    private fun Set<String>.associateToCurrentSplit(values: List<Workout>): PersistentList<Pair<String, Workout?>> {
        val initialSplit = mutableListOf<Pair<String, Workout?>>()
        this.forEachIndexed { index, key ->
            try {
                initialSplit.add(Pair(key, values[index]))
            } catch (e: IndexOutOfBoundsException) {
                initialSplit.add(Pair(key, null))
            }
        }
        return initialSplit.toPersistentList()
    }

    @Immutable
    data class State(
        @DrawableRes
        val background: Int = R.drawable.next_workout_box_background_19,
        val nextWorkout: String = "",
        val moreMenuExpanded: Boolean = false,
        val currentSplit: PersistentList<Pair<String, Workout?>> = persistentListOf(),
        val bigThreeLifts: Triple<Float, Float, Float> = Triple(0f, 0f,0f),
        val pendingNotifications: PersistentList<Notification> = persistentListOf(),
        val currentMonth: CurrentMonth = CurrentMonth(0,0)
    )

    private data class RecentWorkouts(
        val split: PersistentList<Pair<String, Workout?>>,
        val nextWorkout: String
    )
}