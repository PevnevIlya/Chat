package com.example.myapplication.presentation.fragments

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.presentation.composables.CardWithImageAndText
import com.example.myapplication.presentation.main.MainViewModel

@Composable
fun ChatsScreen(
    viewModel: MainViewModel,
    innerPadding: PaddingValues
){
    val placeholderColor = Color.Gray
    val placeholderBitmap = remember {
        Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888).apply {
            eraseColor(placeholderColor.toArgb())
        }
    }
    Log.d("Coroutines", viewModel.user.companionsList.toString())
    LazyColumn(
        contentPadding = PaddingValues(
            bottom = innerPadding.calculateBottomPadding()
        )
    ) {
        items(viewModel.user.companionsList) { item ->
            val user = remember(item) { mutableStateOf<UserModel?>(null) }
            val newBitmap = remember(item) { mutableStateOf<Bitmap?>(null) }
            LaunchedEffect(item) {
                val userInfo = viewModel.getUserInfo(item)
                user.value = userInfo
            }
            user.value?.let { userModel ->
                if (newBitmap.value == null) {
                    viewModel.loadImageFromServer(userModel.photoUrl ?: "empty", newBitmap)
                }

                CardWithImageAndText(
                    bitmap = newBitmap.value ?: placeholderBitmap,
                    text1 = userModel.name,
                    text2 = userModel.status
                ) {
                    viewModel.goToSingleChat = item
                }
            }
        }
    }
}