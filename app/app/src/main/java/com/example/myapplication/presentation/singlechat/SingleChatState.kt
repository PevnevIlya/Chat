package com.example.myapplication.presentation.singlechat

data class SingleChatState(
    var chatId: String,
    var email: String = "",
    var companionEmail: String = "",
    var isLoading: Boolean = false
)