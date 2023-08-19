package com.dev_musashi.weather.presentation.main

import android.location.Address
import com.dev_musashi.weather.domain.data.WeatherData

data class HomeState(
    val address: Address? = null,
    val currentWeather: WeatherData? = null,
    val dayWeather: Map<String, List<WeatherData>>? = null,
    val isLoading: Boolean = false,
    val isLocationError: Boolean = false,
    val loadFail: Boolean = false
)
