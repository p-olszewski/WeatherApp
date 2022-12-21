package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.fragments.FirstFragment
import com.example.weatherapp.fragments.SecondFragment
import com.example.weatherapp.fragments.ThirdFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//https://api.openweathermap.org/data/2.5/weather?q=%C5%81%C3%B3d%C5%BA&appid=2327766b852ee11e6971640e01ded0ef&units=metric
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // sharedPreferences
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        sharedPref.edit()


        // ViewPager
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0-> {
                    tab.text = "Basic"
                }
                1-> {
                    tab.text = "Advanced"
                }
                2-> {
                    tab.text = "Forecast"
                }
            }
        }.attach()
    }
}