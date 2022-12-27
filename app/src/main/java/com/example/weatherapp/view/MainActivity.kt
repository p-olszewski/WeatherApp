package com.example.weatherapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.service.RetrofitInstance
import com.example.weatherapp.service.ViewPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var myResponseBody: WeatherData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareViewPager()
        prepareFloatingActionButtons()
        getWeatherData()
    }

    private fun prepareFloatingActionButtons() {
        val addFAB = findViewById<FloatingActionButton>(R.id.idFABAdd)
        val refreshFAB = findViewById<FloatingActionButton>(R.id.idFABRefresh)
        val saveFAB = findViewById<FloatingActionButton>(R.id.idFABSave)
        var fabVisible = false
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

        refreshFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Syncing data...", Toast.LENGTH_SHORT).show()
            // API
            getWeatherData()
        }

        saveFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Save clicked..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun prepareViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Basic"
                1 -> tab.text = "Advanced"
                2 -> tab.text = "Forecast"
            }
        }.attach()
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateViews() {
        findViewById<TextView>(R.id.tvCityName).text = myResponseBody.name
        findViewById<TextView>(R.id.tvCoords).text = myResponseBody.coord.toString()
        findViewById<TextView>(R.id.tvTemp).text = buildString {
            append(myResponseBody.main.temp)
            append("°C")
        }
        findViewById<TextView>(R.id.tvWeatherDescription).text = myResponseBody.weather[0].description
        findViewById<TextView>(R.id.tvPressure).text = buildString {
            append(myResponseBody.main.pressure.toString())
            append(" hPa")
        }
        val formatter = SimpleDateFormat("hh:mm")
        val time = formatter.format(Calendar.getInstance().time)
        findViewById<TextView>(R.id.tvRefreshTime).text = time
        Log.d("myResponseBody", myResponseBody.toString())
    }

    private fun getWeatherData() {
        val retrofitData = RetrofitInstance.api.getData()
        retrofitData.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
                myResponseBody = response.body()!!
                updateViews()
            }

            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                Log.d("MainActivity", "Error")
            }
        })
    }
}