package com.dev_musashi.weather.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.weather.data.location.LocationTrackerImpl
import com.dev_musashi.weather.data.repository.WeatherRepositoryImpl
import com.dev_musashi.weather.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepositoryImpl,
    private val locationTracker: LocationTrackerImpl
) : ViewModel() {
    var state = mutableStateOf(HomeState())
        private set

    fun initWeather() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                state.value = state.value.copy(address = location.second!!)
                loadCurWeather(location.first!!.latitude, location.second!!.longitude).join()
                loadDayWeather(location.first!!.latitude, location.second!!.longitude).start()
            } else {
                state.value = state.value.copy(isLoading = false, isLocationError = true)
            }
        }
    }

    private fun loadCurWeather(x: Double, y: Double) = viewModelScope.launch(Dispatchers.IO) {
        when (val currentWeather = repository.getCurrentWeather(nx = x, ny = y)) {
            is Resource.Success -> {
                state.value = state.value.copy(currentWeather = currentWeather.data)
            }

            is Resource.Error -> {
                state.value = state.value.copy(
                    currentWeather = null,
                    isLoading = false,
                    loadFail = true
                )
            }
        }
    }

    private fun loadDayWeather(x: Double, y: Double) = viewModelScope.launch(Dispatchers.IO) {
        when (val weatherData = repository.getTodayWeather(nx = x, ny = y)) {
            is Resource.Success -> {
                if (weatherData.data != null) {
                    state.value = state.value.copy(
                        dayWeather = weatherData.data.weatherMap,
                        isLoading = false
                    )
                }
            }

            is Resource.Error -> {
                state.value = state.value.copy(isLoading = false, loadFail = true)
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


