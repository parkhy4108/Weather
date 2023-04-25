package com.dev_musashi.weather.presentation.main.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.weather.domain.data.WeatherData


@Composable
fun HourWeatherCard(
    item: WeatherData,
    modifier: Modifier,
) {
    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(16.dp)
            .width(80.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = item.time.substring(0,2) + ":" + item.time.substring(2,4))
            Image(painter = painterResource(id = item.weatherClassification.iconRes), contentDescription = null)
            Text(text = item.weatherClassification.desc, fontSize = 10.sp)
            Text(text = item.temperature + "°C")
        }
    }
}