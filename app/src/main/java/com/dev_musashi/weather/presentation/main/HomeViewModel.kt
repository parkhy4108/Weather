package com.dev_musashi.weather.presentation.main

import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.util.convertXY
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dev_musashi.weather.domain.location.LocationTracker
import com.dev_musashi.weather.domain.usecase.GetCurrentWeather
import com.dev_musashi.weather.domain.usecase.GetTodayWeather

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodayWeather: GetTodayWeather,
    private val getCurrentWeather: GetCurrentWeather,
    private val locationTracker: LocationTracker
) : ViewModel() {
    var state = mutableStateOf(HomeState())
        private set

    fun loadWeatherData() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            delay(5000)
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                state.value = state.value.copy(address = location.second!!)
                loadCurrentTimeDate(location.first!!.latitude, location.second!!.longitude)
                loadDayTimeDate(location.first!!.latitude, location.second!!.longitude)
            }
        }
    }

    private fun loadCurrentTimeDate(x: Double, y: Double) {
        viewModelScope.launch {
            when (val currentWeather = getCurrentWeather(nx = x, ny = y)) {
                is Resource.Success -> {
                    state.value = state.value.copy(currentWeather = currentWeather.data, isLoading = false)
                }
                is Resource.Error -> {
                    state.value = state.value.copy(currentWeather = null, isLoading = false)
                }
            }
        }
    }

    private fun loadDayTimeDate(x: Double, y: Double) {
        viewModelScope.launch {
            when (val weatherData = getTodayWeather(nx = x, ny = y)) {
                is Resource.Success -> {
                    state.value = state.value.copy(weatherData = weatherData.data?.weatherMap, isLoading = false)
                }
                is Resource.Error -> {
                    state.value = state.value.copy(weatherData = null, isLoading = false)
                }
            }
        }
    }

    fun locationClicked() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { result ->
                state.value = state.value.copy(address = result.second)
            }
        }
    }

}


