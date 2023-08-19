package com.dev_musashi.weather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("getVilageFcst?serviceKey=${Constants.API_KEY}")
    suspend fun getTodayWeather(
        @Query("dataType") dataType : String = "json",
        @Query("numOfRows") numOfRows : Int = 600,
        @Query("pageNo") pageNo : Int = 1,
        @Query("base_date") baseDate : String,
        @Query("base_time") baseTime : String,
        @Query("nx") nx : Int,
        @Query("ny") ny : Int
    ) : Weather

    @GET("getUltraSrtFcst?serviceKey=${Constants.API_KEY}")
    suspend fun getCurrentWeather(
        @Query("dataType") dataType: String = "json",
        @Query("numOfRows") numOfRows: Int = 580,
        @Query("pageNo") pageNo: Int = 1,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): Weather
}