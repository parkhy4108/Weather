package com.dev_musashi.weather.domain.repository

import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.data.WeatherData
import com.dev_musashi.weather.domain.data.WeatherMap

interface WeatherRepository {
    suspend fun getTodayWeather(
        nx: Double,
        ny: Double
    ): Resource<WeatherMap>

    suspend fun getCurrentWeather(
        nx: Double,
        ny: Double
    ): Resource<WeatherData>
}