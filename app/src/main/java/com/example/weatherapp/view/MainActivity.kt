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
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.service.ApiInterface
import com.example.weatherapp.service.RetrofitInstance
import com.example.weatherapp.service.ViewPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.openweathermap.org/"
var temp: String = ""

class MainActivity : AppCompatActivity() {
    // needed!
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    private lateinit var test: TextView
//    lateinit var response


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test = findViewById<TextView>(R.id.testTextView)

        // variables and constants
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tvCityName = viewPager.findViewById<TextView>(R.id.tvCityName)
        val tvTemp = viewPager.findViewById<TextView>(R.id.tvTemp)
        val tvCoords = viewPager.findViewById<TextView>(R.id.tvCoords)
        val tvPressure = viewPager.findViewById<TextView>(R.id.tvPressure)
        val tvRefreshTime = viewPager.findViewById<TextView>(R.id.tvRefreshTime)
        val ivWeatherImage = viewPager.findViewById<TextView>(R.id.ivWeatherImage)
        val addFAB = findViewById<FloatingActionButton>(R.id.idFABAdd)
        val refreshFAB = findViewById<FloatingActionButton>(R.id.idFABRefresh)
        val saveFAB = findViewById<FloatingActionButton>(R.id.idFABSave)
        var fabVisible = false


        // ViewPager
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Basic"
                1 -> tab.text = "Advanced"
                2 -> tab.text = "Forecast"
            }
        }.attach()

        // FloatingActionButton toggle
        addFAB.setOnClickListener {
            if (!fabVisible) {
                refreshFAB.show()
                refreshFAB.visibility = View.VISIBLE
                saveFAB.show()
                saveFAB.visibility = View.VISIBLE
                fabVisible = true
            } else {
                refreshFAB.hide()
                refreshFAB.visibility = View.INVISIBLE
                saveFAB.hide()
                saveFAB.visibility = View.INVISIBLE
                fabVisible = false
            }
        }

        // API
        getMyData()

        refreshFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Refresh clicked..", Toast.LENGTH_SHORT).show()
        }

        saveFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Save clicked..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMyData() {
        val retrofitData = RetrofitInstance.api.getData()
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