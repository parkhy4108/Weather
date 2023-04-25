package com.dev_musashi.weather.presentation.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev_musashi.weather.presentation.main.HomeState

@Composable
fun DayWeather(
    state: HomeState,
    modifier: Modifier = Modifier
) {
    state.weatherData?.let {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            val firstDay = it.keys.first()
            Text(
                text = firstDay.substring(0,4) + "."
                        + firstDay.substring(4, 6) + "."
                        + firstDay.substring(6, 8) + " ",
                color = Color.White
            )
            LazyRow {
                items(it.values.first()) {
                    HourWeatherCard(item = it, modifier = Modifier)
                }
            }
            val secondDay = it.keys.last()
            Text(
                text = secondDay.substring(0..4) + "."
                        + secondDay.substring(4, 6) + "."
                        + secondDay.substring(6, 8) + " ",
                color = Color.White
            )
            LazyRow {
                items(it.values.last()) {
                    HourWeatherCard(item = it, modifier = Modifier)
                }
            }
        }
    }


}