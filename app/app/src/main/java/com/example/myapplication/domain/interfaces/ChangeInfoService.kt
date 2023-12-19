package com.example.myapplication.domain.interfaces

interface ChangeInfoService {

    suspend fun changeUserInfo(name: String, status: String, photoUrl: String): String

    suspend fun getUserInfo(email: String): List<String>

    companion object{
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    sealed class Endpoints(val url: String){
        object ChangeUserInfo: Endpoints("$BASE_URL/changeUserInfo")
        object GetUserInfo: Endpoints("$BASE_URL/getUserInfo")
    }
}