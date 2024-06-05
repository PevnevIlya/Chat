package com.example.myapplication.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.room.UserDao
import com.example.myapplication.presentation.addcompanion.AddCompanionScreen
import com.example.myapplication.presentation.changeInfo.ChangeInfoScreen
import com.example.myapplication.presentation.login.LoginScreen
import com.example.myapplication.presentation.main.MainScreen
import com.example.myapplication.presentation.registration.RegistrationScreen
import com.example.myapplication.presentation.singlechat.SingleChat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.RegistrationScreen.route) {
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
            val applicationContext = LocalContext.current.applicationContext
            ChangeInfoScreen(context = applicationContext,navController = navController)
        }
        composable(Screens.AddCompanionScreen.route){
            AddCompanionScreen(navController = navController)
        }
        composable(
            "${Screens.SingleChatScreen.route}/{companionEmail}/{email}",
            arguments = listOf(
                navArgument("companionEmail") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType})) {
            val companionEmail = it.arguments?.getString("companionEmail") ?: "null"
            val email = it.arguments?.getString("email") ?: "null"
            SingleChat(navController = navController, companionEmail = companionEmail, email = email)
        }
    }
}