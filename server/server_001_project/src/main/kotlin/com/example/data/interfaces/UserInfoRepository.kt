package com.example.data.interfaces

import io.ktor.server.application.ApplicationCall

interface UserInfoRepository {
    suspend fun changeInfo(call: ApplicationCall)

    suspend fun getInfo(call: ApplicationCall)
}