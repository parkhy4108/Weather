package com.dev_musashi.weather.domain.usecase

import com.dev_musashi.weather.data.repository.WeatherRepositoryImpl
import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.data.WeatherData
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(
    private val repository: WeatherRepositoryImpl
) {
    suspend operator fun invoke(nx: Double, ny: Double): Resource<WeatherData> {
        return repository.getCurrentWeather(nx, ny)
    }
}