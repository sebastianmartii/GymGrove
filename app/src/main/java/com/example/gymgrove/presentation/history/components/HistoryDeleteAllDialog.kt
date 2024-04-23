package com.example.gymgrove.presentation.history.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.gymgrove.R

@Composable
fun HistoryDeleteAllDialog(
    onDismissRequest: () -> Unit,
    onDeleteAll: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(id = R.string.remove_everything))
        },
        text = {
            Text(text = stringResource(id = R.string.delete_all_history_confirmation_message))
        },
        confirmButton = {
            TextButton(onClick = {
                onDeleteAll()
                onDismissRequest()
            }) {
                Text(text = stringResource(id = R.string.remove_action))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text(text = stringResource(id = R.string.cancel_action))
            }
        }
    )
}