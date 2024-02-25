package com.example.di

import com.example.data.controllers.RoomController
import com.example.data.controllers.UserController
import com.example.data.implementations.ChatInterfaceImpl
import com.example.data.implementations.UserInfoRepositoryImpl
import com.example.data.implementations.UserRepositoryImpl
import com.example.data.interfaces.ChatInterface
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single<CoroutineDatabase> {
        KMongo.createClient()
            .coroutine
            .getDatabase("message_db")
    }
    single {
        UserController(get())
    }
    single {
        UserRepositoryImpl(get())
    }
    single {
        UserInfoRepositoryImpl(get())
    }
    single<ChatInterface> {
        ChatInterfaceImpl(get())
    }
    single {
        RoomController(get())
    }
}