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

    var seconds by mutableIntStateOf(0)
    var isRunning by mutableStateOf(false)
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
        seconds = 0
    }
    @SuppressLint("DefaultLocale")
    fun formatTime(): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

}