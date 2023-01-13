package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.service.TestData
import com.example.weatherapp.service.RecyclerAdapter

class ThirdFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.third_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.adapter = RecyclerAdapter(createContacts())
    }

    override fun onStart() {
        super.onStart()
//        val apiResponse = (activity as MainActivity).currentApiResponseBody
    }

    private fun createContacts(): List<TestData> = buildList{
        for (i in 0..50) {
            val newTestData = TestData("$i°C")
            add(newTestData)
        }
    }
}