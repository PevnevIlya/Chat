package com.example.myapplication.data.models

data class UserModel(
    val id: String,
    var password: String = "",
    var email: String = "",
    var name: String = "",
    var status: String = "",
    var photoUrl: String = "",
    var companionsList: MutableList<String> = mutableListOf()
)
