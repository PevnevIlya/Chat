package com.example.myapplication.domain.interfaces

import android.net.Uri
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.data.room.User

interface ChangeInfoService {

    suspend fun changeUserInfo(email: String,name: String, status: String, photoUrl: String)

    suspend fun getUserInfo(email: String): UserModel

    suspend fun addCompanion(email: String, companionEmail: String): String

    suspend fun getCompanionList(email: String): String

    companion object{
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    sealed class Endpoints(val url: String){
        object ChangeUserInfo: Endpoints("$BASE_URL/changeUserInfo")
        object GetUserInfo: Endpoints("$BASE_URL/getUserInfo")
        object AddCompanion: Endpoints("$BASE_URL/addCompanionInList")
        object GetCompanionList: Endpoints("$BASE_URL/getUserCompanions")
    }
}