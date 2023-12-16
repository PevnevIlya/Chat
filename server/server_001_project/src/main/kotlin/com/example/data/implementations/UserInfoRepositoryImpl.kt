package com.example.data.implementations

import com.example.data.controllers.UserController
import com.example.data.interfaces.UserInfoRepository
import io.ktor.server.application.ApplicationCall

class UserInfoRepositoryImpl(
    private val userController: UserController
): UserInfoRepository {
    override suspend fun changeInfo(call: ApplicationCall) {
        userController.changeUserInfo(call)
    }

    override suspend fun getInfo(call: ApplicationCall) {
        userController.getUserInfo(call)
    }
}