package com.example.myapplication.presentation.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.presentation.composables.DefaultButton
import com.example.myapplication.presentation.composables.DefaultText
import com.example.myapplication.presentation.main.MainViewModel

@Composable
fun ProfileScreen(
    viewModel: MainViewModel
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(75.dp))
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Your photo")
            Spacer(modifier = Modifier.height(50.dp))
            DefaultText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Your name", color = Color.Black,
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(30.dp))
            DefaultText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Your status", color = Color.Gray,
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(30.dp))
            DefaultButton(
                action = { viewModel.goToChangeInfoActivity = true },
                modifier = Modifier
                    .height(45.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(20.dp)),
                buttonText = "Edit profile",
                textColor = Color.Black,
                buttonColor = Color.White
            )
        }
    }
}