package com.dev_musashi.weather.domain.data

import androidx.annotation.DrawableRes
import com.dev_musashi.weather.R.drawable as AppImg

sealed class WeatherClassification(
    val desc: String,
    @DrawableRes val iconRes: Int
) {
    object Sunny : WeatherClassification(
        desc = "맑음",
        iconRes = AppImg.ic_sunny
    )

    object Cloudy : WeatherClassification(
        desc = "구름낀 하늘",
        iconRes = AppImg.ic_cloudy
    )

    object FullCloudy : WeatherClassification(
        desc = "흐림",
        iconRes = AppImg.ic_fullcloudy
    )

    object SunnyNight : WeatherClassification(
        desc = "맑은 밤",
        iconRes = AppImg.ic_night_clear
    )

    object CloudyNight : WeatherClassification(
        desc = "약간 흐린 밤",
        iconRes = AppImg.ic_night_cloudy
    )

    object Rainy : WeatherClassification(
        desc = "비",
        iconRes = AppImg.ic_rain
    )

    object RainAndSnow : WeatherClassification(
        desc = "비&눈",
        iconRes = AppImg.ic_snow_rain_mix
    )

    object Snow : WeatherClassification(
        desc = "눈",
        iconRes = AppImg.ic_snow_rain_mix
    )

    object Shower : WeatherClassification(
        desc = "소나기",
        iconRes = AppImg.ic_showers
    )

    object Drizzling : WeatherClassification(
        desc = "빗방울",
        iconRes = AppImg.ic_raindrops
    )

    object Sleet : WeatherClassification(
        desc = "빗방울눈날림",
        iconRes = AppImg.ic_sleet
    )

    object SnowBlizzard : WeatherClassification(
        desc = "눈날림",
        iconRes = AppImg.ic_snow_wind
    )

    companion object {
        fun fromCategory(classification: String): WeatherClassification {
            return when (classification) {
                "맑음" -> Sunny
                "구름 많음" -> Cloudy
                "흐림" -> FullCloudy
                "밤맑음" -> SunnyNight
                "밤구름 많음" -> CloudyNight
                "비" -> Rainy
                "비/눈" -> RainAndSnow
                "눈" -> Snow
                "소나기" -> Shower
                "빗방울" -> Drizzling
                "빗방울눈날림" -> Sleet
                "눈날림" -> SnowBlizzard
                else -> Sunny
            }
        }
    }
}