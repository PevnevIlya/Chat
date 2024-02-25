package com.example.myapplication.data.implementations

import android.util.Log
import com.example.myapplication.Resource
import com.example.myapplication.data.models.MessageModel
import com.example.myapplication.data.remote.AddCompanionDto
import com.example.myapplication.data.remote.GetAllMessagesDto
import com.example.myapplication.data.remote.MessageDto
import com.example.myapplication.domain.interfaces.SingleChatService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

class SingleChatServiceImplementation(
    private val client: HttpClient
): SingleChatService {
    private var socket: WebSocketSession? = null
    override suspend fun initSession(email: String, chatId: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${SingleChatService.Endpoints.ChatSocket.url}?email=$email&chatId=$chatId")
            }
            if(socket?.isActive == true) {
                Log.d("ChatTest", "good")
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getChatId(email: String, companionEmail: String): String {
        return try {
            val response = client.post(SingleChatService.Endpoints.GetChatId.url) {
                contentType(ContentType.Application.Json)
                setBody(AddCompanionDto(email = email, companionEmail = companionEmail))
            }
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
            "null"
        }
    }

    override suspend fun getAllMessages(chatId: String): MutableList<MessageDto> {
        return try {
            val response = client.post(SingleChatService.Endpoints.GetAllMessages.url){
                contentType(ContentType.Application.Json)
                setBody(GetAllMessagesDto(chatId))
            }
            return response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
            mutableListOf()
        }
    }

    override suspend fun sendMessage(chatId: String, senderEmail: String, message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<MessageModel> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    Log.d("ChatTest", messageDto.text)
                    messageDto.toMessage()
                } ?: flow {  }
        } catch(e: Exception) {
            e.printStackTrace()
            flow {  }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}