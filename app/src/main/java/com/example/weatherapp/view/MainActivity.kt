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
var temp: String = ""

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    private lateinit var test: TextView
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

        // FloatingActionButton toggle
        binding.idFABAdd.setOnClickListener {
            if (!fabVisible) {
                binding.idFABRefresh.show()
                binding.idFABRefresh.visibility = View.VISIBLE
                binding.idFABSave.show()
                binding.idFABSave.visibility = View.VISIBLE
                fabVisible = true
            } else {
                binding.idFABRefresh.hide()
                binding.idFABRefresh.visibility = View.INVISIBLE
                binding.idFABSave.hide()
                binding.idFABSave.visibility = View.INVISIBLE
                fabVisible = false
            }
        }

        // API
        getMyData()

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
                val myStringBuilder = StringBuilder()
                if (responseBody != null) {
                    myStringBuilder.append(responseBody.main.temp.toString())
                    Log.d("MainActivity", responseBody.main.temp.toString())
                    Log.d("MainActivity", responseBody.main.pressure.toString())
                    Log.d("MainActivity", responseBody.toString())
                    test.text = responseBody.main.temp.toString()
                }
            }

            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                Log.d("MainActivity", "Error")
            }
        })
    }
}