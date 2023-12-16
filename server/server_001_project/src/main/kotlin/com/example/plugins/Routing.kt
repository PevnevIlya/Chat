package com.example.plugins

import com.example.data.controllers.UserController
import com.example.data.routes.configureChangeInfoRouting
import com.example.data.routes.configureLoginRouting
import com.example.data.routes.configureRegisterRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userController by inject<UserController>()
    install(Routing){
        configureRegisterRouting(userController)
        configureLoginRouting(userController)
        configureChangeInfoRouting(userController)
    }
}
