package com.example.runmate2

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.runmate2.Navigations.AppNav
import com.example.runmate2.Navigations.LoginNav
import com.example.runmate2.Navigations.SplashNav
import com.example.runmate2.ui.theme.RunMate2Theme
import android.Manifest
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.runmate2.Backend.DistView
import com.example.runmate2.Backend.distViewFact

class MainActivity : ComponentActivity() {
    private val distView: DistView by viewModels { distViewFact(application) }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted : Boolean ->
        if(isGranted){
            distView.startLocationUpdates()
        }
        else{
            Log.d("DistView", "Permission not granted")
        }

    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunMate2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "spl"){
                        SplashNav(navController)
                        LoginNav(navController)
                        AppNav(navController)
                    }
                }
            }
        }
        if(ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        else{
           Log.d("DistView", "Permission already granted")
            distView.startLocationUpdates()
        }
    }
}