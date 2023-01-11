package com.example.weatherapp.model

data class WeatherForecastModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherList>,
    val message: Int
)