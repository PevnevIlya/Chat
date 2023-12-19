package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.changeInfo.ChangeInfoScreen
import com.example.myapplication.presentation.login.LoginScreen
import com.example.myapplication.presentation.main.MainScreen
import com.example.myapplication.presentation.registration.RegistrationScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(Screens.RegistrationScreen.route){
            RegistrationScreen(navController = navController)
        }
        composable(Screens.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(Screens.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Screens.ChangeInfoScreen.route){
            ChangeInfoScreen(navController = navController)
        }
    }
}