package com.example.data.interfaces

import com.example.data.model.MessageModel

interface ChatInterface {
    suspend fun getAllMessages(chatId: String): MutableList<MessageModel>

    suspend fun insertMessage(chatId: String, message: MessageModel)
}