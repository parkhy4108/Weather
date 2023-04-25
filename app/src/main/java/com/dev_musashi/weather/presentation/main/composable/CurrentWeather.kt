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
    weatherState: WeatherData?,
    address: Address?,
    modifier: Modifier = Modifier,
    locationClick: ()->Unit
) {
    weatherState?.let {
        Card(
            backgroundColor = Color.White,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                            val country = address.adminArea
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
                        text = "Today ${weatherState.time[0]}${weatherState.time[1]} : ${weatherState.time[2]}${weatherState.time[3]}",
                        modifier = Modifier,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = weatherState.weatherClassification.iconRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${weatherState.temperature}°C",
                    fontSize = 45.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CurrentStatus(
                        value = weatherState.precipitationAmount,
                        unit = "",
                        icon = AppImg.ic_raindrops,
                        modifier = Modifier
                    )
                    CurrentStatus(
                        value = weatherState.humidity,
                        unit = "%",
                        icon = AppImg.ic_humidity,
                        modifier = Modifier
                    )
                    CurrentStatus(
                        value = weatherState.wind,
                        unit = "m/s",
                        icon = AppImg.ic_wind,
                        modifier = Modifier
                    )
                }
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