package com.dev_musashi.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev_musashi.weather.presentation.main.MainScreen
import com.dev_musashi.weather.presentation.splash.SplashScreen
import com.dev_musashi.weather.presentation.ui.theme.WeatherTheme
import com.dev_musashi.weather.util.Screen

@Composable
fun MainContent(){
    WeatherTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                scaffoldState = rememberScaffoldState()
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Screen.Splash.route) {
                        SplashScreen(navController)
                    }
                    composable(route = Screen.Home.route) {
                        MainScreen()
                    }
                }
            }
        }
    }
}