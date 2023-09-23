package com.example.weatherapp.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ForecastWeatherRowBinding
import com.example.weatherapp.model.WeatherList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class RecyclerAdapter(private val data: List<WeatherList>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding: ForecastWeatherRowBinding) : ViewHolder(binding.root) {
        val tvDate = binding.tvDate
        val tvWeatherDescription = binding.tvWeatherDescription
        val ivWeatherImage = binding.ivWeatherImage
        val tvTemp = binding.tvTemp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val forecastWeatherRowBinding = ForecastWeatherRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(forecastWeatherRowBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val apiDateString = data[position].dt_txt
        val dataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dataFormat.parse(apiDateString)
        val outputFormat = SimpleDateFormat("HH:mm EEEE, dd MMM", Locale.ENGLISH)
        val outputDateString = date?.let { outputFormat.format(it) }
        holder.tvDate.text = outputDateString

        val weatherDesc = data[position].weather[0].description
        val capitalizedDesc = weatherDesc[0].uppercaseChar() + weatherDesc.substring(1)
        holder.tvWeatherDescription.text = capitalizedDesc
        holder.tvTemp.text = buildString {
            append(data[position].main.temp.roundToInt())
            append("°C")
        }
        when (data[position].weather[0].main) {
            "Thunderstorm" -> holder.ivWeatherImage.setImageResource(R.drawable._729387_weather_cloudy_lightning_cloud_forecast)
            "Clouds" -> holder.ivWeatherImage.setImageResource(R.drawable._729391_cloud_weather_forecast_rain_cloudy)
            "Drizzle" -> holder.ivWeatherImage.setImageResource(R.drawable._729390_weather_drip_forecast_drop_cloud)
            "Rain" -> holder.ivWeatherImage.setImageResource(R.drawable._729383_forecast_rain_cloud_weather_raining)
            "Snow" -> holder.ivWeatherImage.setImageResource(R.drawable._729380_snow_snowflake_cloud_weather_winter_cloudy)
            "Clear" -> holder.ivWeatherImage.setImageResource(R.drawable._729378_sunny_sun_weather_climate_forecast)
            "Mist", "Fog", "Haze" -> holder.ivWeatherImage.setImageResource(R.drawable._729389_weather_foggy_cloudy_forecast_cloud)
            else -> holder.ivWeatherImage.setImageResource(R.drawable._729392_cloudy_sunny_forecast_sun_cloud_weather)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}