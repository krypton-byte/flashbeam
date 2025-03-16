package com.krypton.flashbeam

import android.content.Context
import android.os.BatteryManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krypton.flashbeam.screen.Home
import com.krypton.flashbeam.screen.Screen
import com.krypton.flashbeam.screen.Settings
import com.krypton.flashbeam.models.SettingsModel
import com.krypton.flashbeam.models.Theme
import com.krypton.flashbeam.models.colorSchemes
import com.krypton.flashbeam.ui.theme.MyApplicationTheme
import compose.icons.TablerIcons
import compose.icons.tablericons.Battery
import compose.icons.tablericons.Battery2
import compose.icons.tablericons.Battery4
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Settings
import dev.vivvvek.seeker.Seeker
import dev.vivvvek.seeker.SeekerColors
import dev.vivvvek.seeker.SeekerDefaults
import dev.vivvvek.seeker.SeekerDimensions
import dev.vivvvek.seeker.SeekerState
import dev.vivvvek.seeker.Segment
import dev.vivvvek.seeker.rememberSeekerState
import kotlin.math.round

class Torch(val context: Context)

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
                        if (state.value) settingsModel.value.theme.backgroundOn else settingsModel.value.theme.backrgoundOff, animationSpec = tween(1000)
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