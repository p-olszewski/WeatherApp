package com.example.weatherapp.service

import com.example.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {
    // https://api.openweathermap.org/data/2.5/weather?q=%C5%81%C3%B3d%C5%BA&appid=2327766b852ee11e6971640e01ded0ef
    private val BASE_URL = "https://api.openweathermap.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(): Single<WeatherModel> {
        return api.getData()
    }
}