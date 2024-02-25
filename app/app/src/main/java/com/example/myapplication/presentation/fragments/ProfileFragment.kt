package com.example.myapplication.presentation.fragments

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.presentation.composables.DefaultButton
import com.example.myapplication.presentation.composables.DefaultText
import com.example.myapplication.presentation.main.MainViewModel
import com.squareup.picasso.Picasso
import okhttp3.HttpUrl.Companion.toHttpUrl

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
            AsyncImage(
                model = viewModel.loadedBitmap.value,
                contentDescription = "Photo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            LaunchedEffect(viewModel.user.photoUrl) {

            }
            Spacer(modifier = Modifier.height(50.dp))
            DefaultText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = viewModel.user.name.toString(), color = Color.Black,
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(30.dp))
            DefaultText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = viewModel.user.status.toString(), color = Color.Gray,
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