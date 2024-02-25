package com.example.plugins

import com.example.data.model.JSONmodels.ChatSession
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.util.generateNonce

fun Application.configureSecurity() {
    install(Sessions){
        cookie<ChatSession>("SESSION")
    }

    intercept(ApplicationCallPipeline.Features) {
        if(call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["email"] ?: "noEmail"
            val chatId = call.parameters["chatId"] ?: "null"
            call.sessions.set(ChatSession(username, generateNonce(), chatId))
        }
    }
}