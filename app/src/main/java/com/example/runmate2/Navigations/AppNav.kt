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
import com.example.runmate2.Backend.backview

fun NavGraphBuilder.AppNav(navController: NavController){
    navigation(startDestination = "Home", route = "app"){
        composable("Home"){
            Home(navController, viewModel = AuthViewModel(), view = backview())
        }
        composable("During/{time}/{steps}/{calories}"){ backStackEntry ->
            val time = backStackEntry.arguments?.getString("time")?: "00:00"
            val steps = backStackEntry.arguments?.getString("steps")?: "0"
            val calories = backStackEntry.arguments?.getString("calories")?: "0 CAL"
            During(navController, view = backview(), time = time, steps = steps, calories = calories)
        }
        composable("Congrats"){
            Congrats(navController, backview())
        }
    }

}