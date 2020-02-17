package com.example.umbrella.model

data class DataCurrentWeather(
    val name: String,
    val main: DataCurrentWeatherMain,
    val weather: List<DataCurrentWeatherWeather>
)

data class DataCurrentWeatherMain(
    val temp: String
)

data class DataCurrentWeatherWeather(
    val main: String
)
