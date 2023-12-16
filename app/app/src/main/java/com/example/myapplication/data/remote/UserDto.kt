package com.example.myapplication.data.remote

import com.example.myapplication.data.models.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val password: String
) {
    fun toUserModel(): UserModel {
        return UserModel(
            id = "",
            email = email,
            password = password,
            username = ""
        )
    }
}