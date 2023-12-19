package com.example.myapplication.data.models

data class UserModel(
    val id: String,
    var password: String,
    var email: String,
    var username: String = "",
    var status: String = "",
    var photoUrl: String = ""
)
