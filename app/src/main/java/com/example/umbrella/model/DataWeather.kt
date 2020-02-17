package com.example.umbrella.model

data class DataWeatherList(
   val list: List<DataWeather>
)

data class DataWeather(
    val main : DataWeatherMain,
    val weather: List<DataWeatherWeather>,
    val dt_txt: String
)

data class DataWeatherMain (
    val temp: String
)

data class DataWeatherWeather (
    val main: String,
    val icon: String
)

/**
 * "dt": 1519074000,
"main": {
    "temp": 283.99,
    "temp_min": 281.801,
    "temp_max": 283.99,
    "pressure": 989.94,
    "sea_level": 1029.29,
    "grnd_level": 989.94,
    "humidity": 52,
    "temp_kf": 2.19
},
"weather": [
    {
    "id": 801,
    "main": "Clouds",
    "description": "few clouds",
    "icon": "02d"
    }
],
"clouds": {
    "all": 20
},
"wind": {
    "speed": 3.36,
    "deg": 325.001
},
"rain": {},
"sys": {
    "pod": "d"
},
"dt_txt": "2018-02-19 21:00:00"
 */