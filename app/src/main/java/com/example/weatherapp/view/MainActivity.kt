package com.example.weatherapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
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
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val TAG = "MainActivity"

//TODO Debug functions call queue, "Łódź" overwriting current location
class MainActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    lateinit var apiResponseBody: WeatherData
    private var cityName: String = "Łódź" // default location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uiInit()
        permissionInit()
        getCurrentWeatherData(cityName)
    }

    private fun uiInit() {
        viewPagerInit()
        buttonsInit()
    }

    private fun buttonsInit() {
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
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
            getCurrentWeatherData(apiResponseBody.name)
        }

        saveFAB.setOnClickListener {
            Toast.makeText(this@MainActivity, "Save clicked..", Toast.LENGTH_SHORT).show()
        }

        buttonSearch.setOnClickListener {
            val inputField = findViewById<TextInputEditText>(R.id.cityInput)
            getCurrentWeatherData(inputField.text.toString())
            // exit input field and hide keyboard
            inputField.clearFocus()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(inputField.windowToken, 0)
            inputField.clearComposingText()
        }
    }

    private fun viewPagerInit() {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Basic"
                1 -> tab.text = "Additional"
                2 -> tab.text = "Forecast"
            }
        }.attach()

        viewPager.offscreenPageLimit = 2
        Log.d(TAG, "offScreenLimit: " + viewPager.offscreenPageLimit)
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateViews() {
        // fragment 1
        findViewById<TextView>(R.id.tvCityName).text = apiResponseBody.name
        findViewById<TextView>(R.id.tvCoords).text = buildString {
            append(apiResponseBody.coord.lat)
            append(", ")
            append(apiResponseBody.coord.lon)
        }
        findViewById<TextView>(R.id.tvTemp).text = buildString {
            append(apiResponseBody.main.temp.roundToInt())
            append("°C")
        }
        findViewById<TextView>(R.id.tvWeatherDescription).text =
            apiResponseBody.weather[0].description
        findViewById<TextView>(R.id.tvPressure).text = buildString {
            append(apiResponseBody.main.pressure)
            append(" hPa")
        }
        findViewById<TextView>(R.id.tvRefreshTime).text =
            SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        val ivWeatherImage = findViewById<ImageView>(R.id.ivWeatherImage)
        when (apiResponseBody.weather[0].main) {
            "Thunderstorm" -> ivWeatherImage.setImageResource(R.drawable._729387_weather_cloudy_lightning_cloud_forecast)
            "Clouds" -> ivWeatherImage.setImageResource(R.drawable._729391_cloud_weather_forecast_rain_cloudy)
            "Drizzle" -> ivWeatherImage.setImageResource(R.drawable._729390_weather_drip_forecast_drop_cloud)
            "Rain" -> ivWeatherImage.setImageResource(R.drawable._729383_forecast_rain_cloud_weather_raining)
            "Snow" -> ivWeatherImage.setImageResource(R.drawable._729380_snow_snowflake_cloud_weather_winter_cloudy)
            "Clear" -> ivWeatherImage.setImageResource(R.drawable._729378_sunny_sun_weather_climate_forecast)
            "Mist", "Fog", "Haze" -> ivWeatherImage.setImageResource(R.drawable._729389_weather_foggy_cloudy_forecast_cloud)
            else -> ivWeatherImage.setImageResource(R.drawable._729392_cloudy_sunny_forecast_sun_cloud_weather)
        }

        // fragment 2
        findViewById<TextView>(R.id.tvWindDetails).text = buildString {
            append(apiResponseBody.wind.speed.roundToInt())
            append("m/s, ")
            append(apiResponseBody.wind.deg)
            append("deg")
        }
        findViewById<TextView>(R.id.tvHumidityDetails).text = buildString {
            append(apiResponseBody.main.humidity)
            append("%")
        }
        findViewById<TextView>(R.id.tvVisibilityDetails).text = buildString {
            append(apiResponseBody.visibility)
            append("m")
        }

        Log.d("myResponseBody", apiResponseBody.toString())
    }

    private fun getCurrentWeatherData(city: String) {
        Log.i(TAG, "city param: $city")
        val retrofitData = RetrofitInstance.api.getCurrentWeather(city)
        retrofitData.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
                apiResponseBody = response.body()!!
                updateViews()
                Log.d("MainActivity", "Response body 1: $apiResponseBody")
            }

            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                Log.d("MainActivity", "Error")
            }
        })
    }

    private fun permissionInit() {
        locationPermissionInit()
        checkLocationPermission()
    }

    private fun locationPermissionInit() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.i(TAG, "PERMISSION (callback): GRANTED")
                    Toast.makeText(
                        this@MainActivity,
                        "Location permission granted!",
                        Toast.LENGTH_SHORT
                    ).show()
                    findMyLocation()
                    getCurrentWeatherData(cityName)
                } else {
                    Log.i(TAG, "PERMISSION (callback): NOT GRANTED")
                    Toast.makeText(
                        this@MainActivity,
                        "Location permission not granted...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "PERMISSION: GRANTED")
            findMyLocation()
        } else {
            requestPermissionLauncher.launch(ACCESS_COARSE_LOCATION)
            Log.i(TAG, "PERMISSION: Showed permission window")
        }
    }

    private fun findMyLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val networkLocationListener: LocationListener =
            LocationListener { location ->
                currentLocation = location }

        if (hasNetwork) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }

        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            currentLocation = lastKnownLocationByNetwork
        }

        val geocoder = Geocoder(this, Locale.getDefault())
        val addressList =
            geocoder.getFromLocation(currentLocation!!.latitude, currentLocation!!.longitude, 1)
        if (addressList != null) {
            if (addressList.size > 0) {
                val locatedCity = addressList[0].locality
                Toast.makeText(
                    this@MainActivity,
                    "Located city: $locatedCity",
                    Toast.LENGTH_SHORT
                ).show()
                getCurrentWeatherData(locatedCity)
            }
        }
    }

}