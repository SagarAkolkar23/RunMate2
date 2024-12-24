package com.example.runmate2.Navigations

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.runmate2.Splash

fun NavGraphBuilder.SplashNav(navController: NavController) {
    navigation(startDestination = "Splash", route = "spl"){
        composable("Splash") {
            Splash(modifier = Modifier, navController = navController)
        }
    }
}