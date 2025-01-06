package com.example.runmate2.Navigations

import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.runmate2.App.Congrats
import com.example.runmate2.App.During
import com.example.runmate2.App.Home
import com.example.runmate2.AuthViewModel
import com.example.runmate2.Backend.DistView
import com.example.runmate2.Backend.backview
import com.example.runmate2.Backend.distViewFact

fun NavGraphBuilder.AppNav(navController: NavController){
    navigation(startDestination = "Home", route = "app"){
        composable("Home"){
            Home(navController, viewModel = AuthViewModel(), view = backview())
        }
        composable("During/{time}/{steps}/{calories}/{speed}/{distance}"){ backStackEntry ->
            val time = backStackEntry.arguments?.getString("time")?: "00:00"
            val steps = backStackEntry.arguments?.getString("steps")?: "0"
            val calories = backStackEntry.arguments?.getString("calories")?: "0 CAL"
            val speed = backStackEntry.arguments?.getString("speed")?: "0 m/s"
            val distance = backStackEntry.arguments?.getString("distance")?: "0 m"
            During(navController, view = backview(), time = time, steps = steps, calories = calories, authViewModel = AuthViewModel(),speed = speed, distance = distance)
        }
        composable("Congrats"){
            Congrats(navController, backview())
        }
    }
}