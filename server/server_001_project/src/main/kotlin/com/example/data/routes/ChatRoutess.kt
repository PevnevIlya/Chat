package com.example.data.routes

import com.example.data.controllers.RoomController
import com.example.data.model.ChatIdReceive
import com.example.data.model.JSONmodels.ChatSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: RoomController) {
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if(session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }
        try {
            roomController.onJoin(
                email = session.email,
                sessionId = session.sessionId,
                chatId = session.chatId,
                socket = this
            )
            incoming.consumeEach { frame ->
                if(frame is Frame.Text) {
                    roomController.sendMessage(
                        chatId = session.chatId,
                        senderEmail = session.email,
                        message = frame.readText()
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.tryDisconnect(session.email)
        }
    }
}

fun Route.getAllMessages(roomController: RoomController) {
    post("/messages") {
        val receive = call.receive<ChatIdReceive>()
        call.respond(
            HttpStatusCode.OK,
            roomController.getAllMessages(receive.chatId)
        )
    }
}