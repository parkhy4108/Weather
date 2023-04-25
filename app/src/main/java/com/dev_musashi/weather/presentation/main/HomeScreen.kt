package com.dev_musashi.weather.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.weather.presentation.main.composable.CurrentWeather
import com.dev_musashi.weather.presentation.main.composable.DayWeather

@Composable
fun MainScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state

    val context = LocalContext.current

    val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { viewModel.loadWeatherData() }

    LaunchedEffect(Unit) {
        if (permission.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        ) { viewModel.loadWeatherData() }
        else permissionLauncher.launch(permission)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                CurrentWeather(
                    weatherState = state.currentWeather,
                    address = state.address,
                    locationClick = { viewModel.locationClicked() }
                )
            }
            item { DayWeather(state = state) }
        }
        if (state.isLoading) { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) }
    }
}