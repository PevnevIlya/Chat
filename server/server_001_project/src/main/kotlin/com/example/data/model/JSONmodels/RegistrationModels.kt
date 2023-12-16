package com.example.data.model.JSONmodels

import kotlinx.serialization.Serializable

@Serializable
data class ReceiveRemote(
    val email: String,
    val password: String
)
