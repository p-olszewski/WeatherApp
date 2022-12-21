package com.example.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//https://api.openweathermap.org/data/2.5/weather?q=%C5%81%C3%B3d%C5%BA&appid=2327766b852ee11e6971640e01ded0ef&units=metric
class MainActivity : AppCompatActivity() {
    lateinit var addFAB: FloatingActionButton
    lateinit var refreshFAB: FloatingActionButton
    lateinit var saveFAB: FloatingActionButton
    var fabVisible = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        addFAB = findViewById(R.id.idFABAdd)
        refreshFAB = findViewById(R.id.idFABRefresh)
        saveFAB = findViewById(R.id.idFABSave)

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


        fabVisible = false
        addFAB.setOnClickListener {
            if (!fabVisible) {
                refreshFAB.show()
                refreshFAB.visibility = View.VISIBLE
                saveFAB.show()
                saveFAB.visibility = View.VISIBLE

                // on below line we are checking image icon of add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))


                fabVisible = true
            } else {
                refreshFAB.hide()
                refreshFAB.visibility = View.INVISIBLE
                saveFAB.hide()
                saveFAB.visibility = View.INVISIBLE

                // on below line we are changing image source for add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24))


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