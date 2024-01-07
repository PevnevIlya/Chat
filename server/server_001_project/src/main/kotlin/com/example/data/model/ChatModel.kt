package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatModel(
    val messages: List<MessageModel>,
    val id: String
)
