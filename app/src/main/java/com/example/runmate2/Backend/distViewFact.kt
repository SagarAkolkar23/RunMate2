package com.example.runmate2.Backend

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class distViewFact(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DistView::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DistView(application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}