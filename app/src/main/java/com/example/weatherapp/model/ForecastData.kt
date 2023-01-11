package com.example.weatherapp.model

data class ForecastData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherForecastData>,
    val message: Int
)