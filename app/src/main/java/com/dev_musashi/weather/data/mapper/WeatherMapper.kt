package com.dev_musashi.weather.data.mapper

import android.util.Log
import com.dev_musashi.weather.data.remote.Body
import com.dev_musashi.weather.data.remote.Item
import com.dev_musashi.weather.data.remote.Items
import com.dev_musashi.weather.domain.data.WeatherClassification
import com.dev_musashi.weather.domain.data.WeatherData
import com.dev_musashi.weather.domain.data.WeatherMap
import java.text.SimpleDateFormat
import java.util.*

fun Body.itemsToWeatherData(): WeatherMap {
    val cal: Calendar = Calendar.getInstance()
    val currentHour: String = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
    val currentMinute: String = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time)
    val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    val currentTime = currentHour + currentMinute
    val weatherMap = items
        .toMapGroupByDate()
        .mapValues {
            it.value.groupBy { it.fcstTime }
        }.mapValues {
            if (it.key == currentDate) {
                val filterMap = it.value.filter {
                    it.key.toInt() > currentTime.toInt()
                }
                mapToWeatherDataList(filterMap)
            } else {
                mapToWeatherDataList(it.value)
            }
        }
    return WeatherMap(weatherMap = weatherMap)
}

fun Items.toMapGroupByDate(): Map<String, List<Item>> {
    val cal: Calendar = Calendar.getInstance()
    val currentHour: String = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
    val currentMinute: String = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time)
    val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    val currentTime = currentHour + currentMinute
    val map = item.groupBy { it.fcstDate }.toMutableMap()
    map.forEach {
        println(it.key)
        it.value.forEach {
            println(it)
        }
    }
    Log.d("TAG", "toMapGroupByDate: ${map.keys.size}")
    if (currentTime.toInt() in 2300..2359) {
        map.remove(key = currentDate)
    } else {
        while(map.keys.size>=3) {
            map.remove(key = map.keys.last())
        }
    }
    return map
}

fun mapToWeatherDataList(weatherMapByDate: Map<String, List<Item>>): List<WeatherData> {
    val result = weatherMapByDate.mapValues {
        listToWeatherData(it.value)
    }
    return result.values.toList()
}

fun listToWeatherData(itemList: List<Item>): WeatherData {
    val time = itemList[0].fcstTime
    val temperature = itemList.find { it.category == "T1H" || it.category == "TMP" }!!.fcstValue!!
    val precipitation = when(itemList.find { it.category == "PTY" }?.fcstValue) {
        "0" -> if (time.toInt() in 1800..2359 || time.toInt() == 0 || time.toInt() in 1..600) "밤맑음" else "맑음"
        "1" -> "비"
        "2" -> "비/눈"
        "3" -> "눈"
        "4" -> "소나기"
        "5" -> "빗방울"
        "6" -> "빗방울눈날림"
        else -> "눈날림"
    }
    val precipitationAmount = itemList.find { it.category == "RN1" ||  it.category == "PCP" }!!.fcstValue!!
    val sky = when(itemList.find { it.category == "SKY" }?.fcstValue) {
        "1" -> if (time.toInt() in 1800..2359 || time.toInt() == 0 || time.toInt() in 1..600) "밤맑음" else "맑음"
        "3" -> if (time.toInt() in 1800..2359 || time.toInt() == 0 || time.toInt() in 1..600) "밤구름 많음" else "구름 많음"
        else -> "흐림"
    }
    val humidity = when(itemList.find { it.category == "REH" }?.fcstValue){
        "0", "강수없음", null -> "0"
        else -> itemList.find { it.category == "SKY" }!!.fcstValue!!
    }
    val wind = itemList.find { it.category == "WSD" }?.fcstValue!!
    val weatherClassification =
        if (precipitation == "밤맑음" || precipitation == "맑음") WeatherClassification.fromCategory(sky)
        else WeatherClassification.fromCategory(precipitation)

    return WeatherData(
        time,
        temperature,
        precipitation,
        precipitationAmount,
        sky,
        humidity,
        wind,
        weatherClassification
    )
}

fun Items.toWeatherInfo(): WeatherData {
    return listToWeatherData(
        itemList = item
            .groupBy { it.fcstDate }
            .values.first()
            .sortedBy { it.fcstTime }
            .subList(0,10)
    )
}