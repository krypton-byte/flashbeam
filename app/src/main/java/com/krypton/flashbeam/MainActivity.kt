package com.krypton.flashbeam

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krypton.flashbeam.models.SettingsModel
import com.krypton.flashbeam.screen.Home
import com.krypton.flashbeam.screen.Screen
import com.krypton.flashbeam.screen.Settings
import com.krypton.flashbeam.ui.theme.MyApplicationTheme
import dev.vivvvek.seeker.rememberSeekerState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val batteryView = BatteryView()
        registerReceiver(BatteryBroadcast(batteryView), IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                var state = remember {
                    mutableStateOf(false)
                }
                val seekerState = rememberSeekerState()
                val settingsModel = remember {
                    mutableStateOf(
                        SettingsModel(),
                    )
                }
                val slider = remember {
                    mutableFloatStateOf(50f)
                }
                LaunchedEffect(state.value, settingsModel.value.theme) {
                    settingsModel.value.animateColor.animateTo(
                        if (state.value) settingsModel.value.theme.backgroundOn else settingsModel.value.theme.backrgoundOff,
                        animationSpec = tween(1000)
                    )
                }
                NavHost(navController = navController, startDestination = Screen.Home.name) {
                    composable(route = Screen.Home.name) {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Home(
                                navController,
                                state = state,
                                seekerState = seekerState,
                                modifier = Modifier.padding(innerPadding),
                                settingsModel = settingsModel,
                                slider = slider,
                                battery = batteryView
                            )
                        }
                    }
                    composable(route = Screen.Settings.name) {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Settings(
                                navController,
                                settingsModel = settingsModel,
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .background(settingsModel.value.animateColor.value)

                            )
                        }

                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val state = remember { mutableStateOf(false) }
    val settingsModel = remember {
        mutableStateOf(
            SettingsModel()
        )
    }
    val slider = remember { mutableFloatStateOf(80f) }
    val batteryView = remember { BatteryView() }
    Home(
        navController,
        state = state,
        seekerState = rememberSeekerState(),
        settingsModel = settingsModel,
        slider = slider,
        battery = batteryView
    )
}


@Preview
@Composable
fun SettingsPreview() {
    val navController = rememberNavController()
    val settingsModel = remember {
        mutableStateOf(
            SettingsModel()
        )
    }
    Settings(
        navController,
        settingsModel = settingsModel
    )
}