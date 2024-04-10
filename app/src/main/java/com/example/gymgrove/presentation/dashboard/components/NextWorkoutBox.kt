package com.example.gymgrove.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R

@Composable
fun NextWorkoutBox(
    assignedWorkout: String,
    nextSplitDay: String,
    minHeight: Dp,
    moreMenuExpanded: Boolean,
    onWorkoutAssign: () -> Unit,
    onRest: () -> Unit,
    onMoreMenuExpand: () -> Unit,
    onMoreMenuCollapse: () -> Unit,
    onBackgroundChange: () -> Unit,
    onSplitChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
        ) {
            Text(
                text = assignedWorkout,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Light,
                color = Color.White,
            )
            Text(
                text = nextSplitDay,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .4f),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.extraLarge,
                onClick = {},
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.workout_action),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter)
        ) {
            NextWorkoutBoxAction(
                title = stringResource(id = R.string.assign_workout_action),
                icon = Icons.Default.Edit,
                onActionClick = onWorkoutAssign
            )
            NextWorkoutBoxAction(
                title = stringResource(id = R.string.rest_action),
                icon = Icons.Default.Bed,
                onActionClick = onRest
            )
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
            ) {
                NextWorkoutBoxAction(
                    title = stringResource(id = R.string.more_action),
                    icon = Icons.Default.MoreVert,
                    onActionClick = onMoreMenuExpand
                )
                DropdownMenu(
                    expanded = moreMenuExpanded,
                    onDismissRequest = onMoreMenuCollapse
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = R.string.next_workout_box_change_split_action))
                        },
                        onClick = onSplitChange,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ViewTimeline,
                                contentDescription = stringResource(id = R.string.next_workout_box_change_split_action)
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = R.string.next_workout_box_change_background_action))
                        },
                        onClick = onBackgroundChange,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = stringResource(id = R.string.next_workout_box_change_background_action)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NextWorkoutBoxAction(
    title: String,
    icon: ImageVector,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        IconButton(
            onClick = onActionClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor.copy(alpha = .4f),
                contentColor = contentColor
            ),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}