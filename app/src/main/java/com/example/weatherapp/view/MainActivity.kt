package com.example.weatherapp.view

import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.service.ViewPagerAdapter
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    // needed!
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // sharedPreferences
//        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
//        sharedPref.edit()

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        var cityName = GET.getString("cityName", "Lodz")
        tvCityName.text = cityName
        viewModel.refreshData()
        getLiveData()


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

        refreshFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Refresh clicked..", Toast.LENGTH_SHORT).show()
        }

        saveFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Save clicked..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLiveData() {
        viewModel.weatherData.observe(this, Observer { data ->
            data?.let {

            }
        })
    }
}