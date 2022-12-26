package com.example.weatherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.service.ApiInterface
import com.example.weatherapp.service.ViewPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.openweathermap.org/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var fabVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Basic"
                1 -> tab.text = "Advanced"
                2 -> tab.text = "Forecast"
            }
        }.attach()

        // API
        getMyData()

        // FloatingActionButton toggle
        binding.apply {
            idFABAdd.setOnClickListener {
                if (!fabVisible) {
                    idFABRefresh.show()
                    idFABRefresh.visibility = View.VISIBLE
                    idFABSave.show()
                    idFABSave.visibility = View.VISIBLE
                    fabVisible = true
                } else {
                    idFABRefresh.hide()
                    idFABRefresh.visibility = View.INVISIBLE
                    idFABSave.hide()
                    idFABSave.visibility = View.INVISIBLE
                    fabVisible = false
                }
            }
        }

        binding.idFABRefresh.setOnClickListener {
            Toast.makeText(this@MainActivity, "Refresh clicked..", Toast.LENGTH_SHORT).show()
        }

        binding.idFABSave.setOnClickListener {
            Toast.makeText(this@MainActivity, "Save clicked..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("ApiResponse ok: ", responseBody.toString())
                    binding.testTextView.text = responseBody.main.temp.toString()
                    binding.viewPager.findViewById<TextView>(R.id.tvCityName).text = responseBody.name
//                    binding.viewPager.findViewById<TextView>(R.id.tvCoords).text = responseBody.coord.toString()
                    binding.viewPager.findViewById<TextView>(R.id.tvWeatherDescription).text = responseBody.weather.description
                    binding.viewPager.findViewById<TextView>(R.id.tvPressure).text = responseBody.main.pressure.toString()
                    binding.viewPager.findViewById<TextView>(R.id.tvTemp).text = responseBody.main.temp.toString()
                }
            }

            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                Log.d("ApiResponse error: ", "Error")
            }
        })
    }
}