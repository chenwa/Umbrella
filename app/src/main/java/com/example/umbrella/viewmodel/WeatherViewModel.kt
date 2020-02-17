package com.example.umbrella.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.umbrella.model.Network
import com.example.umbrella.model.DataWeather
import com.example.umbrella.model.DataWeatherList
import retrofit2.Call
import  retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    val dataSet = MutableLiveData<List<DataWeather>>()

    fun getWeatherList(): LiveData<List<DataWeather>> {
        return dataSet
    }

    fun getWeather(zipCode: String, units: String) {
        // https://samples.openweathermap.org/data/2.5/forecast?zip=94040&appid=b6907d289e10d714a6e88b30761fae22
        // https://api.openweathermap.org/data/2.5/forecast?zip=94040&appid=d9363c99f5cdfd3f1aae78c8b5d810d7

        val network = Network("https://api.openweathermap.org/data/2.5/")

        network.initRetrofit().getWeather(
            zipCode, "d9363c99f5cdfd3f1aae78c8b5d810d7", units
        ).enqueue(object : Callback<DataWeatherList> {
            override fun onFailure(call: Call<DataWeatherList>, t: Throwable) {
                Log.d("FAILURE: ", t.toString())
            }

            override fun onResponse(
                call: Call<DataWeatherList>,
                response: Response<DataWeatherList>
            ) {
                dataSet.value = response.body()!!.list
            }
        })
    }
}