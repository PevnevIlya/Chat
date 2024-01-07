package com.example.data.model

data class UserModel(
    val id: String,
    var password: String,
    var email: String,
    var username: String = "",
    var status: String = "",
    var photoUrl: String = "",
    var companionEmails: MutableList<String> = mutableListOf()
)
