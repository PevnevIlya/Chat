package com.example.myapplication.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ChangeDto(
    val email: String,
    val name: String,
    val status: String,
    val photoUrl: String
)

@Serializable
data class GetDto(
    val name: String,
    val status: String,
    val photoUrl: String
)