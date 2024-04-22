package com.example.gymgrove.presentation.creator.notification

import android.text.format.DateFormat
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.R
import com.example.gymgrove.presentation.creator.notification.components.NotificationCreatorScreen
import com.example.gymgrove.presentation.creator.notification.components.UnsavedChangesDialog
import com.example.gymgrove.presentation.util.NavigationHelper
import com.example.gymgrove.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class NotificationCreatorScreen(
    val notificationId: Int? = null
) : Screen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<NotificationCreatorScreenModel, NotificationCreatorScreenModel.Factory> { factory ->
            factory.create(notificationId)
        }
        val state by screenModel.state.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val incorrectInputText = stringResource(id = R.string.notification_creator_incorrect_input_message)

        LaunchedEffect(screenModel.navigationFlow) {
            screenModel.navigationFlow.collectLatest {
                when(it) {
                    NavigationHelper.UiEvent.NavigateBack -> {
                        navigator.pop()
                    }
                }
            }
        }

        val context = LocalContext.current

        NotificationCreatorScreen(
            state = state,
            snackbarHostState = snackbarHostState,
            onTitleChange = screenModel::changeTitle,
            onMessageChange = screenModel::changeMessage,
            onRepeatingChange = screenModel::changeRepeating,
            onSchedule = { hour, minute, title, message, repeating ->
                if (isNotificationValid(hour, minute, title, DateFormat.is24HourFormat(context))) {
                    screenModel.scheduleNotification(hour, minute, title, message, repeating)
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(incorrectInputText)
                    }
                }
            },
            onUnsavedChangesDialogShow = {
                screenModel.showUnsavedChangesDialog().also { showDialog ->
                    if (!showDialog) navigator.pop()
                }
            }
        )

        if (state.unsavedChangesDialog) {
            UnsavedChangesDialog(
                onDismissRequest = screenModel::closeDialog,
                onConfirm = {
                    navigator.pop()
                }
            )
        }
    }

    private fun isNotificationValid(hour: Int, minute: Int, title: String, is24HourFormat: Boolean): Boolean {
        val hourRange = if (is24HourFormat) 0..24 else 0..12
        return hour in hourRange && minute in 0..59 && title.isNotBlank() && title.isNotEmpty()
    }
}