package com.example.gymgrove.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.gymgrove.presentation.home.HomeScreen
import com.example.gymgrove.presentation.onboarding.OnBoardingScreen
import com.example.gymgrove.ui.theme.GymGroveTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !splashScreenViewModel.isReady.value
            }
        }

        setContent {
            GymGroveTheme {
                Navigator(
                    screen = HomeScreen,
                    disposeBehavior = NavigatorDisposeBehavior(
                        disposeNestedNavigators = false,
                        disposeSteps = true
                    )
                ) { navigator ->
                    navigator.saveableState(key = "screenContent") {
                        CurrentScreen()
                    }
                    ShowOnBoarding()
                }
            }
        }
    }

    @Composable
    private fun ShowOnBoarding() {
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            if (!splashScreenViewModel.isOnBoardingCompleted.value) {
                navigator.push(OnBoardingScreen)
            }
        }
    }
}