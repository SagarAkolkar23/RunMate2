package com.example.runmate2.Navigations

import android.app.Application
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.runmate2.App.Congrats
import com.example.runmate2.App.During
import com.example.runmate2.App.Home
import com.example.runmate2.AuthViewModel
import com.example.runmate2.Backend.BackView

fun NavGraphBuilder.AppNav(navController: NavController){
    navigation(startDestination = "Home", route = "app"){
        composable("Home"){
            Home(navController, viewModel = AuthViewModel())
        }
        composable("During"){
            During(navController, view = BackView(LocalContext.current.applicationContext as Application))
        }
        composable("Congrats"){
            Congrats(navController, view = BackView(LocalContext.current.applicationContext as Application))
        }
    }

}