package com.example.weatherapp.service

import com.example.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET

//https://api.openweathermap.org/data/2.5/weather?q=%C5%81%C3%B3d%C5%BA&appid=2327766b852ee11e6971640e01ded0ef&units=metric
interface WeatherAPI {
    @GET("data/2.5/weather?q=%C5%81%C3%B3d%C5%BA&appid=2327766b852ee11e6971640e01ded0ef")
    fun getData(): Single<WeatherModel>
}