package com.example.gymgrove.presentation.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.gymgrove.R
import com.example.gymgrove.presentation.creator.notification.NotificationCreatorScreen
import com.example.gymgrove.presentation.creator.split.SplitSetUpScreen
import com.example.gymgrove.presentation.dashboard.components.DashboardScreen

object DashboardTab : Tab {

    private fun readResolve(): Any = DashboardTab

    override val options: TabOptions
        @Composable
        get() {
            val isSelected = LocalTabNavigator.current.current.key == key
            val icon = if (isSelected) Icons.Default.Dashboard else Icons.Outlined.Dashboard

            return TabOptions(
                index = 0u,
                title = stringResource(id = R.string.dashboard_screen_title),
                icon = rememberVectorPainter(image = icon)
            )
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<DashboardScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        DashboardScreen(
            state = state,
            onNavigateToCurrentMonth = {

            },
            onNotificationClick = { id ->
                navigator.push(NotificationCreatorScreen(id))
            },
            onWorkoutAssign = {},
            onRest = {},
            onMoreMenuExpand = screenModel::expandMoreMenu,
            onMoreMenuCollapse = screenModel::collapseMoreMenu,
            onBackgroundChange = {
                navigator.push(BackgroundsScreen)
            },
            onSplitChange = {
                navigator.push(SplitSetUpScreen)
            }
        )
    }
}