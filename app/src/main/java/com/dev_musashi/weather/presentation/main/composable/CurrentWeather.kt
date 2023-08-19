package com.dev_musashi.weather.presentation.main.composable

import android.location.Address
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.weather.domain.data.WeatherData
import com.dev_musashi.weather.R.drawable as AppImg

@Composable
fun CurrentWeather(
    modifier: Modifier = Modifier,
    address: Address?,
    currentWeather: WeatherData,
    locationClick: ()->Unit
) {
    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = AppImg.ic_my_location),
                        contentDescription = null,
                        modifier = Modifier.clickable { locationClick() }
                    )
                    address?.let {
                        val country = if(address.adminArea.contains("광역|특별".toRegex())){
                            address.adminArea.replace("광역|특별".toRegex(), "")
                        } else address.adminArea
                        val city =if(address.subLocality =="null") "" else address.subLocality
                        val fare = if(address.thoroughfare == "null") "" else address.thoroughfare
                        Text(
                            text = "$country $city $fare",
                            modifier = Modifier,
                            color = Color.Black
                        )
                    }
                }
                Text(
                    text = "Today ${currentWeather.time.substring(0,2)} : ${currentWeather.time.substring(0,2)}",
                    color = Color.Black
                )
            }
            Image(
                painter = painterResource(id = currentWeather.weatherClassification.iconRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "${currentWeather.temperature}°C",
                fontSize = 45.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrentStatus(
                    value = currentWeather.precipitationAmount,
                    unit = "",
                    icon = AppImg.ic_raindrops,
                    modifier = Modifier
                )
                CurrentStatus(
                    value = currentWeather.humidity,
                    unit = "%",
                    icon = AppImg.ic_humidity,
                    modifier = Modifier
                )
                CurrentStatus(
                    value = currentWeather.wind,
                    unit = "m/s",
                    icon = AppImg.ic_wind,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun CurrentStatus(
    value: String,
    unit: String,
    @DrawableRes icon: Int,
    modifier: Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value + unit
        )
    }
}