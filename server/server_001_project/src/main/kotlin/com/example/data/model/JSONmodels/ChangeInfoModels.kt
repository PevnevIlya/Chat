package com.example.data.model.JSONmodels

import kotlinx.serialization.Serializable

@Serializable
data class ChangeInfoReceiveRemote(
    val email: String,
    var username: String = "",
    var status: String = "",
    var photoUrl: String = ""
)

@Serializable
data class ChangeInfoResponseRemote(
    var username: String,
    var status: String,
    var photoUrl: String
)

