package com.example.data.routes

import com.example.data.controllers.UserController
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.configureLoginRouting(userController: UserController){
    post("/login") {
        userController.loginUser(call)
    }
}