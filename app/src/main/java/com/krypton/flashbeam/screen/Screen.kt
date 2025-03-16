package com.krypton.flashbeam.screen

sealed class Screen(
    val name: String
) {
    object Home : Screen("Home")
    object Settings : Screen("Settings")
}