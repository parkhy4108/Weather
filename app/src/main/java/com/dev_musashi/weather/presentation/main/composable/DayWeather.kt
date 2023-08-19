package com.dev_musashi.weather.presentation.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev_musashi.weather.domain.data.WeatherData

@Composable
fun DayWeather(
    dayWeather: Map<String, List<WeatherData>>,
    modifier: Modifier = Modifier
) {
    val firstDayDate = dayWeather.keys.first()
    val firstDayWeather = dayWeather.values.first()
    val secondDayDate = dayWeather.keys.last()
    val secondDayWeather = dayWeather.values.last()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = firstDayDate.substring(0,4) + "."
                    + firstDayDate.substring(4, 6) + "."
                    + firstDayDate.substring(6, 8) + " ",
            color = Color.White
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(firstDayWeather) { HourWeatherCard(item = it, modifier = Modifier) }
        }
        Text(
            text = secondDayDate.substring(0..4) + "."
                    + secondDayDate.substring(4, 6) + "."
                    + secondDayDate.substring(6, 8) + " ",
            color = Color.White
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(secondDayWeather) { HourWeatherCard(item = it, modifier = Modifier) }
        }
    }
}