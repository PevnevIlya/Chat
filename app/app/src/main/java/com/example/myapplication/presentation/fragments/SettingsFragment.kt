package com.example.myapplication.presentation.fragments

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.composables.DefaultButton
import com.example.myapplication.presentation.main.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel
){
    DefaultButton(
        action = {
                 viewModel.goToAddCompanionActivity = true
        },
        modifier = Modifier
            .height(45.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(20.dp)),
        buttonText = "Add companion",
        textColor = Color.Black,
        buttonColor = Color.White
    )
}