package com.example.data.model

import io.ktor.websocket.WebSocketSession


data class SocketMember(
    val email: String,
    val sessionId: String,
    val chatId: String,
    val socket: WebSocketSession
)
