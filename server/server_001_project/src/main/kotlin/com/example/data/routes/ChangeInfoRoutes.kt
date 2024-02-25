package com.example.data.routes

import com.example.data.controllers.UserController
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.configureChangeInfoRouting(userController: UserController){
    post("/changeUserInfo") {
        userController.changeUserInfo(call)
    }

    post("/getUserInfo") {
        userController.getUserInfo(call)
    }

    post("/addCompanionInList") {
        userController.addCompanion(call)
    }

    post("/getUserCompanions") {
        userController.getUserCompanions(call)
    }
    post("/getChatId") {
        userController.getChatId(call)
    }
}