package com.example.data.interfaces

import io.ktor.server.application.ApplicationCall

interface UserRepository {
    suspend fun registerNewUser(call: ApplicationCall)

    suspend fun loginUser(call: ApplicationCall)
}