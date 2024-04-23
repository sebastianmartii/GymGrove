package com.example.gymgrove.presentation.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R
import com.example.gymgrove.presentation.util.LabeledCheckbox

@Composable
fun HistoryDeleteDialog(
    onDismissRequest: () -> Unit,
    onDelete: (Boolean) -> Unit
) {
    var removeExercises by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(id = R.string.remove_action))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(id = R.string.delete_workout_confirmation_message))

                LabeledCheckbox(
                    label = stringResource(id = R.string.delete_workout_checkbox_action),
                    checked = removeExercises,
                    onCheckedChange = { removeExercises = it }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDelete(removeExercises)
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