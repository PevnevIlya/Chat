package com.example

import com.example.di.mainModule
import com.example.plugins.configureCustomRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin){
        modules(mainModule)
    }
    configureSerialization()
    configureCustomRouting()
}


