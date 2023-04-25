package com.dev_musashi.weather.domain.usecase

import com.dev_musashi.weather.data.repository.WeatherRepositoryImpl
import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.data.WeatherMap
import javax.inject.Inject

class GetTodayWeather @Inject constructor(
    private val repository: WeatherRepositoryImpl
) {
    suspend operator fun invoke(nx: Double, ny: Double): Resource<WeatherMap> {
        return repository.getTodayWeather(nx, ny)
    }
}