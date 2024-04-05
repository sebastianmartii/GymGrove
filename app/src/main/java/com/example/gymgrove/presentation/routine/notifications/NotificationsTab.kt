package com.example.gymgrove.presentation.routine.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.R
import com.example.gymgrove.domain.notification.model.Notification
import com.example.gymgrove.presentation.creator.notification.NotificationCreatorScreen
import com.example.gymgrove.presentation.routine.TabContent
import com.example.gymgrove.presentation.util.AppBar
import com.example.gymgrove.presentation.util.EmptyScreen
import com.example.gymgrove.presentation.util.LoadingScreen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun Screen.notificationsTab(): TabContent {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = getScreenModel<NotificationsScreenModel>()
    val state by screenModel.state.collectAsStateWithLifecycle()

    return TabContent(
        title = stringResource(id = R.string.notifications_title),
        searchQuery = state.searchQuery,
        onSearchQueryChange = screenModel::changeSearchQuery,
        content = {
            when {
                state.isLoading -> {
                    LoadingScreen()
                }
                state.isEmpty -> {
                    EmptyScreen(message = stringResource(id = R.string.empty_notification_screen_message))
                }
                else -> {
                    NotificationsScreenContent(
                        items = state.items,
                        onItemEdit = { notificationId ->
                            navigator.push(NotificationCreatorScreen(notificationId))
                        },
                        onItemDelete = screenModel::showCancelNotificationDialog
                    )

                    state.dialog?.let { dialog ->
                        CancelNotificationDialog(
                            onDismissRequest = screenModel::closeDialog,
                            onCancel = {
                                screenModel.deleteNotification(dialog.notification)
                            }
                        )
                    }
                }
            }
        },
        actions = persistentListOf(
            AppBar.AppBarAction(
                title = stringResource(id = R.string.navigate_to_workout_creator_action),
                icon = Icons.Default.Add,
                onClick = {
                    navigator.push(NotificationCreatorScreen())
                }
            )
        )
    )
}

@Composable
private fun NotificationsScreenContent(
    items: ImmutableList<Notification>,
    onItemEdit: (Int) -> Unit,
    onItemDelete: (Notification) -> Unit
) {
    LazyColumn {
        items(items) { notification ->
            ListItem(
                headlineContent = {
                    Text(text = notification.title)
                },
                supportingContent = {
                    Text(text = notification.message)
                },
                overlineContent = {
                    if (notification.repeating) {
                        Text(text = stringResource(id = R.string.notification_creator_repeating_label))
                    }
                },
                leadingContent = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = notification.hour.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = notification.minute.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                },
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = {
                            onItemEdit(notification.id)
                        }) {
                            Icon(
                                imageVector = Icons.Default.ModeEdit,
                                contentDescription = stringResource(id = R.string.edit_saved_notification_action)
                            )
                        }
                        IconButton(onClick = {
                            onItemDelete(notification)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete_saved_notification_action)
                            )
                        }
                    }
                }
            )
        }
    }
}