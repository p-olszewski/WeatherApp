package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.service.ViewPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // variables and constants
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val addFAB = findViewById<FloatingActionButton>(R.id.idFABAdd)
        val refreshFAB = findViewById<FloatingActionButton>(R.id.idFABRefresh)
        val saveFAB = findViewById<FloatingActionButton>(R.id.idFABSave)
        var fabVisible = false

        // sharedPreferences
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        sharedPref.edit()

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
}