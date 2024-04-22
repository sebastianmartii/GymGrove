package com.example.gymgrove.presentation.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface NavigationHelper {

    val navigationChannel: Channel<UiEvent>

    val navigationFlow: Flow<UiEvent>

    sealed interface UiEvent {
        data object NavigateBack : UiEvent
    }
}