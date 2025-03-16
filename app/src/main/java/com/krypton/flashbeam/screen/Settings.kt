package com.krypton.flashbeam.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.krypton.flashbeam.components.SelectOption
import com.krypton.flashbeam.models.SettingsModel
import com.krypton.flashbeam.models.Theme
import com.krypton.flashbeam.models.brightness
import com.krypton.flashbeam.models.colorSchemes
import com.krypton.flashbeam.models.timers
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowLeft


@Composable
fun Settings(
    navhost: NavHostController,
    settingsModel: MutableState<SettingsModel>,
    modifier: Modifier = Modifier
) {
    val expandedTheme = remember { mutableStateOf(false) }
    val expandedTimer = remember { mutableStateOf(false) }
    val expandedBrightness = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    TablerIcons.ArrowLeft,
                    contentDescription = "",
                    tint = settingsModel.value.theme.text,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navhost.popBackStack()
                        })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Settings",
                    fontWeight = FontWeight.Bold,
                    color = settingsModel.value.theme.text,
                    fontSize = 23.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Appearance",
                    color = settingsModel.value.theme.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Dark Mode", color = settingsModel.value.theme.text, fontSize = 16.sp)
                    Switch(
                        settingsModel.value.theme == Theme.Dark,
                        onCheckedChange = { it ->
                            settingsModel.value =
                                settingsModel.value.copy(theme = if (it) Theme.Dark else Theme.Light)
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = settingsModel.value.schemeColor.light)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Theme Color", fontSize = 16.sp, color = settingsModel.value.theme.text)
                    SelectOption(
                        expandedTheme,
                        colorSchemes,
                        settingsModel.value.schemeColor,
                        onChange = {
                            settingsModel.value = settingsModel.value.copy(schemeColor = it)
                        },
                        modifier = Modifier.background(settingsModel.value.animateColor.value),
                        color = settingsModel.value.theme.text
                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                        .border(5.dp, settingsModel.value.theme.text)
                )
                Text(
                    "Flashlight",
                    color = settingsModel.value.theme.text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Auto-Off Timer", color = settingsModel.value.theme.text, fontSize = 16.sp)
                    SelectOption(
                        expandedTimer,
                        timers,
                        settingsModel.value.autoOffTimer,
                        onChange = {
                            settingsModel.value.autoOffTimer = it

                        },
                        modifier = Modifier.background(settingsModel.value.animateColor.value),
                        color = settingsModel.value.theme.text
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Screen Flash", color = settingsModel.value.theme.text, fontSize = 16.sp)
                    Switch(
                        settingsModel.value.screenFlash,
                        onCheckedChange = {
                            settingsModel.value = settingsModel.value.copy(screenFlash = it)
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = settingsModel.value.schemeColor.light)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Default Brightness",
                        color = settingsModel.value.theme.text,
                        fontSize = 16.sp
                    )
                    SelectOption(
                        expandedBrightness,
                        brightness,
                        settingsModel.value.defaultBrightness,
                        onChange = {
                            settingsModel.value.defaultBrightness = it
                        },
                        modifier = Modifier.background(settingsModel.value.animateColor.value),
                        color = settingsModel.value.theme.text
                    )
                }
                Spacer(Modifier.height(10.dp))
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                        .border(5.dp, settingsModel.value.theme.text)
                )
                Text(
                    "Notifications",
                    color = settingsModel.value.theme.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Battery Alert", color = settingsModel.value.theme.text, fontSize = 16.sp)
                    Switch(
                        settingsModel.value.batteryAlert,
                        onCheckedChange = { it ->
                            settingsModel.value = settingsModel.value.copy(batteryAlert = it)
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = settingsModel.value.schemeColor.light)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Vibration Feedback",
                        fontSize = 16.sp,
                        color = settingsModel.value.theme.text
                    )
                    Switch(
                        settingsModel.value.vibrationFeedback,
                        onCheckedChange = {
                            settingsModel.value = settingsModel.value.copy(vibrationFeedback = it)
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = settingsModel.value.schemeColor.light)
                    )
                }
            }
        }
        Text("Flashbeam v1.0", color = settingsModel.value.theme.text)
    }
}


