package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager
//https://api.openweathermap.org/data/2.5/weather?q=%C5%81%C3%B3d%C5%BA&appid=2327766b852ee11e6971640e01ded0ef&units=metric
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // sharedPreferences
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        sharedPref.edit()





        // fragments
        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()

        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().apply {
            replace(R.id.fragment1, firstFragment).commit()
        }

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            fm.beginTransaction().apply {
                replace(R.id.fragment1, firstFragment).addToBackStack(null).commit()
            }
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            fm.beginTransaction().apply {
                replace(R.id.fragment1, secondFragment).addToBackStack(null).commit()
            }
        }
    }
}