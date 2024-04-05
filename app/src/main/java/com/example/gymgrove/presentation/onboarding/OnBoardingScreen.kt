package com.example.gymgrove.presentation.onboarding

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.presentation.creator.split.SplitSetUpScreen
import com.example.gymgrove.presentation.home.HomeScreen
import com.example.gymgrove.presentation.onboarding.components.AlarmsStep
import com.example.gymgrove.presentation.onboarding.components.ExercisesStep
import com.example.gymgrove.presentation.onboarding.components.HistoryStep
import com.example.gymgrove.presentation.onboarding.components.NotificationPermissionTextProvider
import com.example.gymgrove.presentation.onboarding.components.NotificationsStep
import com.example.gymgrove.presentation.onboarding.components.OnBoardingScreen
import com.example.gymgrove.presentation.onboarding.components.PermissionDialog
import com.example.gymgrove.presentation.onboarding.components.SplitStep
import com.example.gymgrove.presentation.onboarding.components.WorkoutsStep
import com.example.gymgrove.presentation.util.Screen
import kotlinx.collections.immutable.persistentListOf

object OnBoardingScreen : Screen() {
    private fun readResolve(): Any = OnBoardingScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<OnBoardingScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val activity = LocalContext.current as Activity

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (!isGranted) {
                    screenModel.showDialog()
                }
            }
        )

        val navigateToSplitSetUpScreen = { navigator.push(SplitSetUpScreen) }

        OnBoardingScreen(
            tabs = persistentListOf(
                ExercisesStep(),
                WorkoutsStep(),
                NotificationsStep(
                    onPostNotificationPermissionRequest = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                ),
                AlarmsStep(
                    onScheduleExactAlarmsPermissionRequest = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            activity.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                        }
                    }
                ),
                HistoryStep(),
                SplitStep(
                    state.selectedSplit,
                    screenModel::changeSplit,
                    navigateToSplitSetUpScreen
                )
            ),
            selectedSplit = state.selectedSplit,
            onComplete = {
                screenModel.completeOnBoarding(state.selectedSplit?.splitWorkouts!!)
                navigator.pop()
                navigator.push(HomeScreen)
            }
        )

        if (state.isPermissionDialogVisible) {
            PermissionDialog(
                permissionTextProvider = NotificationPermissionTextProvider(),
                onDeny = screenModel::closeDialog,
                onAllow = {
                    activity.openAppSettings()
                }
            )
        }
    }

    private fun Activity.openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        startActivity(intent)
    }
}