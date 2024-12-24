package com.example.runmate2.Navigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.runmate2.AuthViewModel
import com.example.runmate2.Logins.Login
import com.example.runmate2.Logins.SignUp
import com.example.runmate2.Splash

fun NavGraphBuilder.LoginNav(navController: NavController) {
    navigation(startDestination = "Login", route = "auth") {

        composable("Login") {
            Login(navController, viewModel = AuthViewModel())
        }
        composable("SignUp") {
            SignUp(navController, viewModel = AuthViewModel())
        }
    }
}