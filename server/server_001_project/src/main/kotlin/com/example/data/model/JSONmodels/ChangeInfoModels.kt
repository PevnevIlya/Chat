package com.example.data.model.JSONmodels

import kotlinx.serialization.Serializable

@Serializable
data class ChangeInfoReceiveRemote(
    val email: String,
    var name: String = "",
    var status: String = "",
    var photoUrl: String = ""
)

@Serializable
data class ChangeInfoResponseRemote(
    var name: String,
    var status: String,
    var photoUrl: String
)

@Serializable
data class AddCompanionReceiveRemote(
    val email: String,
    val companionEmail: String
)

@Serializable
data class GetUserCompanionsRemote(
    val list: MutableList<String>
)
@Serializable
data class GetChatIdReceive(
    val email: String,
    val companionEmail: String
)

