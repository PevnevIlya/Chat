package com.example.data.interfaces

import com.example.data.model.MessageModel

interface ChatInterface {
    suspend fun getAllMessages(): List<MessageModel>

    suspend fun insertMessage(message: MessageModel)
}