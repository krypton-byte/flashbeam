package com.krypton.flashbeam.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.krypton.flashbeam.BatteryView
import com.krypton.flashbeam.models.SettingsModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Bolt
import compose.icons.tablericons.Settings
import dev.vivvvek.seeker.Seeker
import dev.vivvvek.seeker.SeekerDefaults
import dev.vivvvek.seeker.SeekerState
import dev.vivvvek.seeker.Segment


@Composable
fun Home(
    navhost: NavHostController,
    state: MutableState<Boolean>,
    seekerState: SeekerState,
    slider: MutableFloatState,
    battery: BatteryView,
    settingsModel: MutableState<SettingsModel>,
    modifier: Modifier = Modifier,
) {
    var slider by remember { slider }
    val segments = listOf(
        Segment(name = "Dim", start = 50f), Segment(name = "Medium", start = 60f),
        Segment(name = "Bright", start = 70f)
    )
    var level = (slider / 100 * 200).dp
    val haptic = LocalHapticFeedback.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(settingsModel.value.animateColor.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(battery.icon(), contentDescription = "ya", tint = Color.Gray)
                Text("${battery.percentage.value}%", color = Color.Gray)
            }
            Icon(
                TablerIcons.Settings,
                contentDescription = "settings",
                tint = Color.Gray,
                modifier = Modifier.clickable {
                    navhost.navigate(Screen.Settings.name)
                })
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(350.dp)
                    .clip(CircleShape)
                    .clickable(indication = null, onClick = {
                        state.value = !state.value
                        if (settingsModel.value.vibrationFeedback) haptic.performHapticFeedback(
                            HapticFeedbackType.LongPress
                        )
                    }, interactionSource = remember { MutableInteractionSource() })
                    .drawBehind {
                        drawCircle(
                            if (state.value) settingsModel.value.schemeColor.buttonOn else settingsModel.value.theme.buttonOff,
                            radius = 230f
                        )
                        drawCircle(
                            Brush.radialGradient(
                                listOf(settingsModel.value.schemeColor.light, Color.Transparent),
                                radius = level.toPx()
                            ), radius = if (state.value) level.toPx() else 0f
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    TablerIcons.Bolt,
                    contentDescription = "Electricity",
                    modifier = Modifier.size(100.dp),
                    tint = if (state.value) Color.White else Color.Gray
                )
            }
            Text(
                "Intensity: ${if (state.value) (10 + (slider - 50) / 30 * 90).toInt() else 0}%",
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
            Seeker(
                state = seekerState,
                value = if (state.value) slider else 50f,
                onValueChange = {
                    slider = it
                },
                range = 50f..80f,
                segments = segments,
                dimensions = SeekerDefaults.seekerDimensions(trackHeight = 10.dp),
                colors = SeekerDefaults.seekerColors(
                    trackColor = Color.LightGray,
                    thumbColor = settingsModel.value.theme.thumbColor,
                    progressColor = settingsModel.value.schemeColor.light,
                    disabledTrackColor = Color.LightGray,
                    disabledThumbColor = settingsModel.value.theme.thumbColor,
                    disabledProgressColor = settingsModel.value.schemeColor.light
                ),
                enabled = state.value
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                if (state.value) "${seekerState.currentSegment.name} Mode" else "OFF",
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Text("Continuous beam with adjustable intensity", color = Color.Gray)
        }
        Text("Flashbeam v1.0", color = Color.Gray)
    }
}