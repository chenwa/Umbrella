package com.example.umbrella.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitEndpoint {
    // api.openweathermap.org/data/2.5/forecast?zip={zip code},{country code}&appid={your api key}

    @GET("forecast")
    fun getWeather(
        @Query("zip") zipCode: String,
        @Query("appid") appKey: String,
        @Query("units") units: String
    ): Call<DataWeatherList>
}
