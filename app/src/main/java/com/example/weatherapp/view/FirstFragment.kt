package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weatherapp.R

class FirstFragment : Fragment() {
//    lateinit var tvCityName: TextView
//    lateinit var tvTemp: TextView
//    lateinit var tvCoords: TextView
//    lateinit var tvPressure: TextView
//    lateinit var tvRefreshTime: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.first_fragment, container, false)
//        val testTemp = view.findViewById<TextView>(R.id.tvTemp)
//        testTemp.text = "123"
        return view
    }

    fun updateCityName(text: String) {
        val tvCityName = view?.findViewById<TextView>(R.id.tvCityName)
//        val tvTemp = view?.findViewById<TextView>(R.id.tvTemp)
//        val tvCoords = view?.findViewById<TextView>(R.id.tvCoords)
//        val tvPressure = view?.findViewById<TextView>(R.id.tvPressure)
//        val tvRefreshTime = view?.findViewById<TextView>(R.id.tvRefreshTime)

        tvCityName?.text = text
    }
}