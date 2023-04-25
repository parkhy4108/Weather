package com.dev_musashi.weather.util

fun calCurrentWeatherTime(h: Int, m: Int): String =
    if (m < 45) {
        if (h == 0) "2330"
        else {
            val resultH = h - 1
            if (resultH < 10) "0" + resultH.toString() + "30"
            else resultH.toString() + "30"
        }
    } else h.toString() + "30"

fun calDayWeatherTime(h: Int, m: Int): String = when (h) {
    in 2 until 5 -> if (h == 2 && m < 10) "2310" else "0210"
    in 5 until 8 -> if (h == 5 && m < 10) "0210" else "0510"
    in 8 until 11 -> if (h == 8 && m < 10) "0510" else "0810"
    in 11 until 14 -> if (h == 11 && m < 10) "0810" else "1110"
    in 14 until 17 -> if (h == 14 && m < 10) "1110" else "1410"
    in 17 until 20 -> if (h == 17 && m < 10) "1410" else "1710"
    in 20 until 23 -> if (h == 20 && m < 10) "1710" else "2010"
    else -> if (h == 23 && m < 10) "2010" else "2310"
}

