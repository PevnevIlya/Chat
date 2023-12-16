package com.example.data.implementations

import com.example.data.controllers.UserController
import com.example.data.interfaces.UserRepository
import io.ktor.server.application.ApplicationCall

class UserRepositoryImpl(
    private val userController: UserController
): UserRepository {
    override suspend fun registerNewUser(call: ApplicationCall) {
        userController.registerNewUser(call)
    }

    override suspend fun loginUser(call: ApplicationCall) {
        userController.loginUser(call)
    }
}