package com.example.weatherapp.service

import com.example.weatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.http.GET
// https://api.openweathermap.org/data/2.5/weather?q=Lodz&appid=2327766b852ee11e6971640e01ded0ef&units=metric
interface ApiInterface {
    @GET("data/2.5/weather?q=Warsaw&appid=2327766b852ee11e6971640e01ded0ef&units=metric")
    fun getData(): Call<WeatherData>
}