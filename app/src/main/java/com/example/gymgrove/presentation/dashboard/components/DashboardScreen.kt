package com.example.gymgrove.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.gymgrove.R
import com.example.gymgrove.presentation.dashboard.DashboardScreenModel
import com.example.gymgrove.presentation.util.DotSeparator
import com.example.gymgrove.presentation.util.HorizontalPagerIndicator

private const val secondaryContentAlpha = .7f

@Composable
fun DashboardScreen(
    state: DashboardScreenModel.State,
    onNavigateToCurrentMonth: () -> Unit,
    onNotificationClick: (Int) -> Unit,
    onWorkoutAssign: () -> Unit,
    onRest: () -> Unit,
    onMoreMenuExpand: () -> Unit,
    onMoreMenuCollapse: () -> Unit,
    onBackgroundChange: () -> Unit,
    onSplitChange: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val notificationsPagerState = rememberPagerState(pageCount = { state.pendingNotifications.size })

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding(),
                    top = 8.dp
                )
        ) {
            Image(
                painter = painterResource(id = state.background),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight.times(.4f) + 80.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            DashboardAppBar(title = stringResource(id = R.string.dashboard_screen_title))
            NextWorkoutBox(
                assignedWorkout = "Push Day A",
                nextSplitDay = state.nextWorkout,
                minHeight = screenHeight.times(0.4f),
                moreMenuExpanded = state.moreMenuExpanded,
                onWorkoutAssign = onWorkoutAssign,
                onRest = onRest,
                onMoreMenuExpand = onMoreMenuExpand,
                onMoreMenuCollapse = onMoreMenuCollapse,
                onBackgroundChange = onBackgroundChange,
                onSplitChange = onSplitChange
            )
            Box {
                Box(modifier = Modifier
                    .padding(top = 16.dp)
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.background)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    HorizontalPager(state = notificationsPagerState) {
                        val notification = state.pendingNotifications[it]
                        ListItem(
                            overlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = stringResource(id = R.string.notifications_title),
                                        modifier = Modifier
                                            .alpha(secondaryContentAlpha)
                                            .size(16.dp)
                                            .padding(end = 4.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.notifications_title),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier
                                            .alpha(secondaryContentAlpha)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    if (notification.hour in 0..24) {
                                        Text(
                                            text = stringResource(
                                                id = R.string.notification_time_argument_text,
                                                notification.hour,
                                                notification.minute
                                            ),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier
                                                .alpha(secondaryContentAlpha)
                                        )
                                    }
                                }
                            },
                            headlineContent = {
                                Text(text = notification.title)
                            },
                            supportingContent = {
                                Text(text = notification.message)
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                supportingColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                headlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                overlineColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier
                                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                                .clip(MaterialTheme.shapes.large)
                                .clickable {
                                    onNotificationClick(notification.id)
                                }
                        )
                    }
                    HorizontalPagerIndicator(
                        pagerState = notificationsPagerState,
                        indicatorWidth = 6.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ) {
                        Column {
                            SectionTitle(title = stringResource(id = R.string.dashboard_current_split_section_title))
                            state.currentSplit.forEach { (splitDay, workout) ->
                                if (workout == null) {
                                    ListItem(
                                        headlineContent = {
                                            Text(text = stringResource(id = R.string.dashboard_current_split_empty_workout_message))
                                        },
                                        leadingContent = {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .size(42.dp)
                                                ) {
                                                    Text(
                                                        text = splitDay.first().toString(),
                                                        style = MaterialTheme.typography.titleLarge,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = MaterialTheme.colorScheme.onPrimary,
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                            .align(Alignment.Center)
                                                    )
                                                }
                                                Text(
                                                    text = splitDay,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        },
                                        colors = ListItemDefaults.colors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            supportingColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            headlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            overlineColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                    )
                                } else {
                                    ListItem(
                                        headlineContent = {
                                            Row {
                                                Text(text = workout.name)
                                                DotSeparator()
                                                Text(text = workout.day)
                                            }
                                        },
                                        leadingContent = {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .background(MaterialTheme.colorScheme.secondary)
                                                        .size(42.dp)
                                                ) {
                                                    Text(
                                                        text = splitDay.first().toString(),
                                                        style = MaterialTheme.typography.titleLarge,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = MaterialTheme.colorScheme.onTertiary,
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                            .align(Alignment.Center)
                                                    )
                                                }
                                                Text(
                                                    text = splitDay,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        },
                                        supportingContent = {
                                            Text(text = stringResource(id = R.string.sets_argument_text, workout.totalWorkingSets))
                                        },
                                        trailingContent = {
                                            Text(text = stringResource(id = R.string.minutes_argument_text, workout.durationInMinutes))
                                        },
                                        colors = ListItemDefaults.colors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            supportingColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            headlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            overlineColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                    )
                                }
                            }
                        }
                    }
                    CurrentMonthWorkoutsCard(
                        workoutsDone = state.currentMonth.count,
                        workoutsDuration = state.currentMonth.duration,
                        onNavigateToCurrentMonth = onNavigateToCurrentMonth,
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            )
                    )
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ) {
                        Column {
                            SectionTitle(title = stringResource(id = R.string.dashboard_big_three_lifts_section_title))
                            repeat(3) {
                                val (title, icon, weight) = when(it) {
                                    1 -> Triple(
                                        stringResource(id = R.string.squat),
                                        painterResource(id = R.drawable.dark_theme_squat_icon),
                                        state.bigThreeLifts.second
                                    )
                                    2 -> Triple(
                                        stringResource(id = R.string.deadlift),
                                        painterResource(id = R.drawable.dark_theme_deadlift_icon),
                                        state.bigThreeLifts.third
                                    )
                                    else -> Triple(
                                        stringResource(id = R.string.bench_press),
                                        painterResource(id = R.drawable.dark_theme_benchpress_icon),
                                        state.bigThreeLifts.first
                                    )
                                }
                                ListItem(
                                    headlineContent = {
                                        Text(text = title)
                                    },
                                    leadingContent = {
                                        Icon(
                                            painter = icon,
                                            contentDescription = title
                                        )
                                    },
                                    supportingContent = {
                                        Text(text = stringResource(id = R.string.weight_argument_text, weight))
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        supportingColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        headlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        overlineColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Normal,
        modifier = modifier
            .alpha(secondaryContentAlpha)
            .padding(
                top = 8.dp,
                start = 8.dp
            )
    )
}