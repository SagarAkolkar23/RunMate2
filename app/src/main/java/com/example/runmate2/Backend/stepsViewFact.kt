package com.example.runmate2.Backend

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class stepsViewFact(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepsView::class.java)){
            @Suppress("UNCHECKED_CAST")
            return StepsView(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}