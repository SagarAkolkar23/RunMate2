package com.example.runmate2.Backend

import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class DistView(application: Application) : AndroidViewModel(application) {
    private val fusedLocationClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private val _distance = MutableLiveData<Int>()
    val distance : LiveData<Int> get() = _distance
    private val _speed = MutableLiveData<Int>()
    val speed : LiveData<Int> get() = _speed
    private var lastLoc : Location? = null
    private var totalDist = 0

    init{
        startLocationUpdates()
    }

    fun startLocationUpdates(){
        val locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    Log.d("DistView", "Location received: $location")
                    if(lastLoc != null){
                     val distance = lastLoc!!.distanceTo(location).toInt()
                        totalDist += distance
                        val speed = location.speed.toInt()
                        Log.d("DistView", "Distance: $distance, Speed: $speed")
                        updateValues(totalDist, speed)
                    }
                    lastLoc = location
                }
            }
        }
        if(ActivityCompat.checkSelfPermission(getApplication<Application>()
            .applicationContext,
                android.Manifest.permission
                    .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper())
        }
        else {
            Log.e("DistView", "Location permission not granted")
        }
    }
    private fun updateValues(totalDist : Int, speed : Int){
        _distance.postValue(totalDist)
        _speed.postValue(speed)
    }
    fun resetDistance(){
        _distance.value = 0
    }
}