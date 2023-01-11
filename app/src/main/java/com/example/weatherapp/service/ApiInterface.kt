package com.example.weatherapp.service

import com.example.weatherapp.model.ForecastData
import com.example.weatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("data/2.5/weather?&appid=2327766b852ee11e6971640e01ded0ef&units=metric")
    fun getCurrentWeather(@Query("q") city: String): Call<WeatherData>

    // https://api.openweathermap.org/data/2.5/forecast?q=Warsaw&cnt=9&appid=2327766b852ee11e6971640e01ded0ef
    @GET("data/2.5/forecast?&cnt=9&appid=2327766b852ee11e6971640e01ded0ef")
    fun getForecastWeather(@Query("q") city: String): Call<ForecastData>
}