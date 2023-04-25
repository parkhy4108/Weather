package com.dev_musashi.weather.util

sealed class Screen(val route : String) {
    object Splash : Screen(route = "SPLASH")
    object Home : Screen(route = "MAIN")
}