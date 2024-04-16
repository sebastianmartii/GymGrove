package com.example.gymgrove.presentation.creator.notification.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R
import com.example.gymgrove.presentation.creator.notification.NotificationCreatorScreenModel
import com.example.gymgrove.presentation.util.LabeledCheckbox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCreatorScreen(
    state: NotificationCreatorScreenModel.State,
    snackbarHostState: SnackbarHostState,
    onTitleChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onRepeatingChange: (Boolean) -> Unit,
    onSchedule: (hour: Int, minute: Int, title: String, message: String, repeating: Boolean) -> Unit,
    onUnsavedChangesDialogShow: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = state.hour,
        initialMinute = state.minute,
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onUnsavedChangesDialogShow) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_back_action)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onSchedule(timePickerState.hour, timePickerState.minute, state.title, state.message, state.repeating)
                    }) {
                        Text(text = stringResource(id = R.string.schedule_notification_action))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            TimeInput(state = timePickerState)
            LabeledCheckbox(
                label = stringResource(id = R.string.notification_creator_repeating_label),
                checked = state.repeating,
                onCheckedChange = onRepeatingChange
            )
            TextField(
                value = state.title,
                onValueChange = onTitleChange,
                label = {
                    Text(text = stringResource(id = R.string.notifications_creator_title_label))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = state.message,
                onValueChange = onMessageChange,
                label = {
                    Text(text = stringResource(id = R.string.notifications_creator_message_label))
                },
                supportingText = {
                    Text(text = stringResource(id = R.string.optional_supporting_text))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}