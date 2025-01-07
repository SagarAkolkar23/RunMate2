package com.example.runmate2.Navigations

import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.runmate2.AuthViewModel
import com.example.runmate2.Logins.Login
import com.example.runmate2.Logins.SignUp
import com.example.runmate2.Logins.resetPassword
import com.example.runmate2.Splash
import com.example.runmate2.googleViewModel

fun NavGraphBuilder.LoginNav(navController: NavController) {

    navigation(startDestination = "Login", route = "auth") {

        composable("Login") {
            Login(navController, viewModel = AuthViewModel(), googleViewModel(Application()))
        }
        composable("SignUp") {
            SignUp(navController, viewModel = AuthViewModel())
        }
        composable("resetPassword") {
            resetPassword(navController,  authViewModel = AuthViewModel())
        }
    }
}