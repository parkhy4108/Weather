package com.dev_musashi.weather.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.weather.presentation.main.composable.CurrentWeather
import com.dev_musashi.weather.presentation.main.composable.DayWeather

@Composable
fun MainScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(Unit) { viewModel.initWeather() }
    LaunchedEffect(key1 = state.isLocationError) {
        scaffoldState.snackbarHostState.showSnackbar(
            "위치 설정 오류가 발생했습니다. 잠시후 다시 시도해주시길 바랍니다.",
            duration = SnackbarDuration.Short
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        state.currentWeather?.let {
            CurrentWeather(
                address = state.address,
                currentWeather = state.currentWeather!!,
                locationClick = { viewModel.locationClicked() }
            )
        }

        state.dayWeather?.let {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item { DayWeather(dayWeather = state.dayWeather!!) }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}