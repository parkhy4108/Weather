package com.dev_musashi.weather.domain.data

data class WeatherData(
    val time : String,
    val temperature : String,
    val precipitation : String,
    val precipitationAmount: String,
    val sky : String,
    val humidity : String,
    val wind : String,
    val weatherClassification: WeatherClassification
)
