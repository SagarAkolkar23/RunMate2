package com.example.runmate2.Backend

import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.Manifest
import com.google.android.gms.location.LocationServices


class BackView(application: Application) : AndroidViewModel(application) {
    private val focusedLocationProvider = LocationServices.getFusedLocationProviderClient(application)
    private var prevLocation : Location? = null
     var totalDistance : Double = 0.0
     var currentSpeed : Double = 0.0
    val distanceLiveData = MutableLiveData<Double>(0.0)
    val speedLiveData = MutableLiveData<Double>(0.0)

    init {
        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                locationResult.locations.forEach { location ->
                    calculateDistanceAndSpeed(location)
                }
            }
        }


        if (checkLocationPermission()) {
            focusedLocationProvider.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun checkLocationPermission() : Boolean{
        val context = getApplication<Application>().applicationContext
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun calculateDistanceAndSpeed(location: Location){
        prevLocation?.let {
            val distance = it.distanceTo(location)
            totalDistance += distance
            val timeDifference = (location.time - it.time) / 1000.0

            if(timeDifference > 0){
                currentSpeed = (distance / timeDifference)
            }
        }
        prevLocation = location

        distanceLiveData.postValue(totalDistance)
        speedLiveData.postValue(currentSpeed * 3.6)
    }
}