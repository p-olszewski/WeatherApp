package com.example.weatherapp.model

data class WeatherList(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: MainForecast,
    val pop: Double,
    val rain: Rain,
    val sys: SysForecast,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: WindForecast
)