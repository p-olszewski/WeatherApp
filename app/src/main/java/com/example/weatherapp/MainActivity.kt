package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManager

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