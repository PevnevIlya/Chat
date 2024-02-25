package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatModel(
    val user1Email: String,
    val user2Email: String,
    val messages: MutableList<MessageModel>,
    val id: String
)

@Serializable
data class ChatIdReceive(
    val chatId: String
)
