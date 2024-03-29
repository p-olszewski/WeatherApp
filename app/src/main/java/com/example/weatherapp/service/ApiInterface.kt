package com.example.weatherapp.service

import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.model.WeatherCurrentModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("data/2.5/weather?&appid=2327766b852ee11e6971640e01ded0ef&units=metric")
    fun getCurrentWeather(@Query("q") city: String): Call<WeatherCurrentModel>

    @GET("data/2.5/forecast?&appid=2327766b852ee11e6971640e01ded0ef&units=metric")
    fun getForecastWeather(@Query("q") city: String): Call<WeatherForecastModel>
}