package com.dev_musashi.weather.data.repository

import com.dev_musashi.weather.data.mapper.itemsToWeatherData
import com.dev_musashi.weather.data.mapper.toWeatherInfo
import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.repository.WeatherRepository
import com.dev_musashi.weather.data.remote.WeatherApi
import com.dev_musashi.weather.domain.data.WeatherData
import com.dev_musashi.weather.domain.data.WeatherMap
import com.dev_musashi.weather.util.calCurrentWeatherTime
import com.dev_musashi.weather.util.calDayWeatherTime
import com.dev_musashi.weather.util.convertXY
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    private val cal: Calendar = Calendar.getInstance()
    private val hour: Int = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time).toInt()
    private val min: Int = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time).toInt()
    private val todayWeatherTime = calDayWeatherTime(hour, min)
    private val currentWeatherTime = calCurrentWeatherTime(hour, min)

    private val todayWeatherDate = if (hour.toInt() < 2) {
        cal.add(Calendar.DAY_OF_YEAR,-1)
        SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    } else if (hour.toInt() == 2 && min.toInt() <= 10) {
        cal.add(Calendar.DAY_OF_YEAR,-1)
        SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    } else {
        SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    }

    private val currentWeatherDate = if (hour == 0) {
        cal.add(Calendar.DAY_OF_YEAR, -1)
        SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    } else SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)

    override suspend fun getTodayWeather(
        nx: Double,
        ny: Double
    ): Resource<WeatherMap> {
        val point = convertXY(nx, ny)
        return try {
            Resource.Success(
                data = weatherApi.getTodayWeather(
                    baseDate = todayWeatherDate,
                    baseTime = todayWeatherTime,
                    nx = point.x,
                    ny = point.y
                ).response.body.itemsToWeatherData()
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "error")
        }
    }

    override suspend fun getCurrentWeather(
        nx: Double,
        ny: Double
    ): Resource<WeatherData> {
        val point = convertXY(nx, ny)
        return try {
            Resource.Success(
                data = weatherApi.getCurrentWeather(
                    baseDate = currentWeatherDate,
                    baseTime = currentWeatherTime,
                    nx = point.x,
                    ny = point.y
                ).response.body.items.toWeatherInfo()
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "error")
        }
    }

}

