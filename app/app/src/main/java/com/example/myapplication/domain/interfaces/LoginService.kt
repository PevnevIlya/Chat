package com.example.myapplication.domain.interfaces

interface LoginService {

    suspend fun login(email: String, password: String): String

    companion object{
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    sealed class Endpoints(val url: String){
        object Login: Endpoints("$BASE_URL/login")
    }
}