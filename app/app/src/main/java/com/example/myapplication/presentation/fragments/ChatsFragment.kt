package com.example.myapplication.presentation.fragments

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.presentation.composables.CardWithImageAndText
import com.example.myapplication.presentation.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatsScreen(
    viewModel: MainViewModel
){
    LazyColumn {
        items(viewModel.user.companionsList) { item ->
            val user = remember(item) { mutableStateOf<UserModel?>(null) }
            LaunchedEffect(item) {
                val userInfo = viewModel.getUserInfo(item)
                user.value = userInfo

            }
            user.value?.let { userModel ->
                CardWithImageAndText(
                    imageUrl = "empty",
                    text1 = userModel.name,
                    text2 = userModel.status,
                    action = {
                        viewModel.goToSingleChat = item
                    }
                )
            }
        }
    }
}