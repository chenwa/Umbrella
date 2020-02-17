package com.example.umbrella.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.umbrella.model.DataCurrentWeather
import com.example.umbrella.model.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherViewModel : ViewModel() {
    val data = MutableLiveData<DataCurrentWeather>()

    fun getCurrentWeather(): LiveData<DataCurrentWeather> {
        return data
    }

    fun getCurrentWeather(zipCode: String, units: String) {
        // https://samples.openweathermap.org/data/2.5/weather?zip=94040&appid=b6907d289e10d714a6e88b30761fae22
        // https://api.openweathermap.org/data/2.5/weather?zip=94040&appid=d9363c99f5cdfd3f1aae78c8b5d810d7

        val network = Network("https://api.openweathermap.org/data/2.5/")

        network.initRetrofit().getCurrentWeather(
            zipCode, "d9363c99f5cdfd3f1aae78c8b5d810d7", units
        ).enqueue(object : Callback<DataCurrentWeather> {
            override fun onFailure(call: Call<DataCurrentWeather>, t: Throwable) {
                Log.d("FAILURE log: ", t.toString())
            }
            override fun onResponse(
                call: Call<DataCurrentWeather>,
                response: Response<DataCurrentWeather>
            ) {
                data.value = response.body()!!
            }
        })
    }
}