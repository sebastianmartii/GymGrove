package com.example.gymgrove.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.gymgrove.presentation.dashboard.DashboardTab
import com.example.gymgrove.presentation.history.HistoryTab
import com.example.gymgrove.presentation.routine.RoutineTab
import com.example.gymgrove.presentation.util.Screen
import kotlinx.collections.immutable.persistentListOf

private const val TAB_NAVIGATOR_KEY = "HomeTabs"

object HomeScreen : Screen() {
    private fun readResolve(): Any = HomeScreen

    private val tabs = persistentListOf(
        DashboardTab,
        RoutineTab,
        HistoryTab
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TabNavigator(
            tab = DashboardTab,
            key = TAB_NAVIGATOR_KEY
        ) { tabNavigator ->
            CompositionLocalProvider(LocalNavigator provides navigator) {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            tonalElevation = 0.5.dp
                        ) {
                            tabs.onEach { tab ->
                                TabNavigationItem(tab = tab)
                            }
                        }
                    },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                    ) {
                        AnimatedContent(
                            targetState = tabNavigator.current,
                            label = "tabContent"
                        ) {
                            tabNavigator.saveableState(key = "currentTab", it) {
                                it.Content()
                            }
                        }
                    }
                }
            }
        }

    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = {
                tabNavigator.current = tab
            },
            icon = {
                Icon(painter = tab.options.icon!!, contentDescription = tab.options.title)
            },
            label = {
                Text(
                    text = tab.options.title,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
        )
    }
}