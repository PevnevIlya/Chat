package com.example.data.controllers

import com.example.data.interfaces.ChatInterface
import com.example.data.model.MessageModel
import com.example.data.model.SocketMember
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val chatInterface: ChatInterface
) {
    private val members = ConcurrentHashMap<String, SocketMember>()
    fun onJoin(
        email: String,
        sessionId: String,
        chatId: String,
        socket: WebSocketSession
    ) {
        members[email] = SocketMember(
            email = email,
            sessionId = sessionId,
            chatId = chatId,
            socket = socket
        )
    }

    suspend fun sendMessage(chatId: String,senderEmail: String, message: String) {
        members.values.forEach { member ->
            val messageEntity = MessageModel(
                text = message,
                email = senderEmail,
                timestamp = System.currentTimeMillis()
            )
            chatInterface.insertMessage(chatId = chatId , message = messageEntity)

            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun getAllMessages(chatId: String): MutableList<MessageModel> {
        return chatInterface.getAllMessages(chatId)
    }

    suspend fun tryDisconnect(email: String) {
        members[email]?.socket?.close()
        if (members.containsKey(email)) {
            members.remove(email)
        }
    }
}