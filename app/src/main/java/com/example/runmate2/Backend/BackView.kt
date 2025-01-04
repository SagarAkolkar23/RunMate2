package com.example.runmate2.Backend

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class backview : ViewModel() {

    var TotalTime by mutableIntStateOf(0)
    var seconds by mutableIntStateOf(0)
        private set

    var isRunning by mutableStateOf(false)
        private set

    private var timerJob: Job? = null

    fun startTimer() {
        if (timerJob == null || !isRunning) {
            isRunning = true
            timerJob?.cancel()  // Cancel any previous job to prevent duplicate timers
            timerJob = viewModelScope.launch {
                while (isRunning) {
                    delay(1000)
                    seconds++
                    Log.d("Timer", seconds.toString())

                }
            }
        }
    }

    fun stopTimer() {
        isRunning = false
        timerJob?.cancel()
        timerJob = null
    }

    fun resetTimer() {
        stopTimer()
        seconds = 0
    }

    fun onStop(){
        seconds = TotalTime
    }

    @SuppressLint("DefaultLocale")
    fun formatTime(): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
    @SuppressLint("DefaultLocale")
    fun formatTime2(): String {
        val minutes = TotalTime / 60
        val remainingSeconds = TotalTime % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}