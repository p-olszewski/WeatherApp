package com.example.weatherapp.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherapp.databinding.ForecastWeatherRowBinding
import com.example.weatherapp.model.WeatherForecastModel
import com.example.weatherapp.model.WeatherList

class RecyclerAdapter(private val data: List<WeatherList>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding: ForecastWeatherRowBinding): ViewHolder(binding.root) {
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
        holder.tvTemp.text = data[position].main.temp.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}