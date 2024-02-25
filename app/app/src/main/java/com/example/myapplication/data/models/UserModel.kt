package com.example.myapplication.data.models

import android.net.Uri

data class UserModel(
    val id: String,
    var password: String = "",
    var email: String = "",
    var name: String = "",
    var status: String = "",
    var photoUrl: String? = "",
    var photoUri: Uri? = null,
    var companionsList: MutableList<String> = mutableListOf()
)
