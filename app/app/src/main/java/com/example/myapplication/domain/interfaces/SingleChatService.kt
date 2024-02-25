package com.example.myapplication.domain.interfaces

import com.example.myapplication.Resource
import com.example.myapplication.data.models.MessageModel
import com.example.myapplication.data.remote.MessageDto
import kotlinx.coroutines.flow.Flow

interface SingleChatService {
    suspend fun initSession(
        email: String,
        chatId: String
    ): Resource<Unit>
    suspend fun getChatId(email: String, companionEmail: String): String
    suspend fun getAllMessages(chatId: String): MutableList<MessageDto>
    suspend fun sendMessage(chatId: String,senderEmail: String, message: String)
    fun observeMessages(): Flow<MessageModel>

    suspend fun closeSession()
    companion object{
        const val BASE_URL = "http://10.0.2.2:8080"
        const val BASE_URL_WS = "ws://10.0.2.2:8080"
    }
    sealed class Endpoints(val url: String){
        object GetAllMessages: Endpoints("$BASE_URL/messages")
        object GetChatId: Endpoints("$BASE_URL/getChatId")
        object ChatSocket : Endpoints("$BASE_URL_WS/chat-socket")
    }
}