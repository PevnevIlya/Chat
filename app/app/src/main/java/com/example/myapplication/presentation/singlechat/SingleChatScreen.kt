package com.example.myapplication.presentation.singlechat

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SingleChat(
    navController: NavController,
    companionEmail: String,
    viewModel: SingleChatViewModel = hiltViewModel()
) {
    Text(text = companionEmail)
}