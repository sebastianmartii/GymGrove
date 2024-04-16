package com.example.gymgrove.presentation.creator.notification

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.example.gymgrove.domain.notification.repository.NotificationRepository
import com.example.gymgrove.domain.notification.use_cases.ScheduleNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationCreatorScreenModel @AssistedInject constructor(
    @Assisted val notificationId: Int? = null,
    private val scheduleNotification: ScheduleNotification,
    private val notificationRepository: NotificationRepository
) : StateScreenModel<NotificationCreatorScreenModel.State>(State()) {

    private var initialState = State()

    private val navigationChannel = Channel<UiEvent>()
    val navigationFlow = navigationChannel.receiveAsFlow()

    init {
        screenModelScope.launch {
            if (notificationId != null && notificationId != 0) {
                notificationRepository.getNotification(notificationId).apply {
                    mutableState.update {
                        it.copy(
                            title = title,
                            message = message,
                            hour = hour,
                            minute = minute,
                            isLoading = false
                        )
                    }
                    initialState = state.value
                }
            } else {
                mutableState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun changeMessage(newMessage: String) {
        mutableState.update {
            it.copy(
                message = newMessage
            )
        }
    }

    fun changeTitle(newTitle: String) {
        mutableState.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun changeRepeating(repeating: Boolean) {
        mutableState.update {
            it.copy(
                repeating = repeating
            )
        }
    }

    fun scheduleNotification(hour: Int, minute: Int, title: String, message: String, repeating: Boolean) {
        mutableState.update {
            it.copy(
                isLoading = true
            )
        }
        screenModelScope.launch {
            scheduleNotification.schedule(notificationId, hour, minute, title, message, repeating)
            navigationChannel.send(UiEvent.NavigateBack)
        }
    }

    fun showUnsavedChangesDialog(): Boolean {
        val hasStateChanged = state.value.hour != initialState.hour || state.value.minute != initialState.minute
                || state.value.repeating != initialState.repeating || state.value.title != initialState.title
                || state.value.message != initialState.message
        if (!hasStateChanged) return false
        mutableState.update {
            it.copy(
                unsavedChangesDialog = true
            )
        }
        return true
    }

    fun closeDialog() {
        mutableState.update {
            it.copy(
                unsavedChangesDialog = false
            )
        }
    }

    @Immutable
    data class State(
        val title: String = "",
        val message: String = "",
        val hour: Int = 0,
        val minute: Int = 0,
        val repeating: Boolean = false,
        val unsavedChangesDialog: Boolean = false,
        val isLoading: Boolean = true
    )

    @AssistedFactory
    interface Factory : ScreenModelFactory {
        fun create(notificationId: Int?): NotificationCreatorScreenModel
    }

    sealed interface UiEvent {
        data object NavigateBack : UiEvent
    }
}