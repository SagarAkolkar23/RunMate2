package com.example.runmate2.Backend

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StepsView(application: Application) : AndroidViewModel(application) {
    private val sensormanager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val _stepCounter = MutableStateFlow(0)
    val stepCount = _stepCounter.asStateFlow()


    private val stepListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let{
                viewModelScope.launch {
                    _stepCounter.value += 1
                }
            }
        }
        override fun onAccuracyChanged(sensor : Sensor?, accuracy : Int) {  }
    }
    init{
        val stepSensor = sensormanager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if(stepSensor != null){
            sensormanager.registerListener(stepListener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    override fun onCleared(){
        super.onCleared()
        sensormanager.unregisterListener(stepListener)
    }

     fun resetSteps(){
        _stepCounter.value = 0
    }
}