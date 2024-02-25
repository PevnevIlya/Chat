package com.example.myapplication.data.models

import kotlinx.serialization.Serializable

data class MessageModel(
    val text: String,
    val email: String,
    val formattedTime: String
)
