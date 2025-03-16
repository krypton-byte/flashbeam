package com.krypton.flashbeam.models

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


abstract class AbstractOptionItem(
    open val name: String,
)

data class SchemeColor(
    override val name: String,
    val buttonOn: Brush,
    val light: Color
) : AbstractOptionItem(name)

data class Timer(
    override val name: String,
    val value: Int
) : AbstractOptionItem(name)

data class Brightness(
    override val name: String,
    val value: Float
) : AbstractOptionItem(name)

val colorSchemes = listOf<SchemeColor>(
    SchemeColor(
        "Yellow",
        Brush.radialGradient(
            listOf(
                Color(red = 254, green = 179, blue = 8),
                Color(red = 255, green = 228, blue = 48, alpha = 255)
            )
        ),
        Color(250, 231, 88, 255)
    ),
    SchemeColor(
        "Green",
        Brush.radialGradient(
            listOf(
                Color(red = 39, green = 174, blue = 96),
                Color(red = 111, green = 219, blue = 84, alpha = 255)
            )
        ),
        Color(144, 238, 144)
    ),
    SchemeColor(
        "Red",
        Brush.radialGradient(
            listOf(
                Color(red = 231, green = 76, blue = 60),
                Color(red = 255, green = 107, blue = 83, alpha = 255)
            )
        ),
        Color(255, 182, 193)
    ),
    SchemeColor(
        "Purple",
        Brush.radialGradient(
            listOf(
                Color(red = 142, green = 68, blue = 173),
                Color(red = 187, green = 99, blue = 214, alpha = 255)
            )
        ),
        Color(216, 191, 216)
    ),
)

enum class Theme(
    val backrgoundOff: Color,
    val backgroundOn: Color,
    val text: Color,
    val thumbColor: Color,
    val buttonOff: Brush
) {
    Dark(
        Color.Black,
        Color(
            red = 17,
            green = 24,
            blue = 39
        ),
        Color.White,
        Color.White,
        Brush.radialGradient(listOf(Color(55, 65, 81), Color(17, 24, 39)))
    ),
    Light(
        Color.White,
        Color.White,
        Color.Gray,
        Color.Gray,
        Brush.radialGradient(listOf(Color(166, 166, 166), Color(201, 201, 201)))
    )
}

val brightness = listOf<Brightness>(
    Brightness("Low (25%)", 0.25f),
    Brightness("Medium (50%)", 0.5f),
    Brightness("High (75%)", 0.75f),
    Brightness("Maximum (100%)", 0.75f)
)
val timers = listOf<Timer>(
    Timer("Never", 0),
    Timer("1 Minutes", 60),
    Timer("5 Minutes", 60 * 5),
    Timer("10 Minutes", 60 * 10),
    Timer("30 Minutes", 60 * 30)
)

data class SettingsModel(
    var theme: Theme = Theme.Dark,
    val animateColor: Animatable<Color, AnimationVector4D> = Animatable(Theme.Dark.backrgoundOff),
    var schemeColor: SchemeColor = colorSchemes[0],
    var autoOffTimer: Timer = timers[0],
    var screenFlash: Boolean = false,
    var defaultBrightness: Brightness = brightness[0],
    var batteryAlert: Boolean = false,
    var vibrationFeedback: Boolean = false
)
