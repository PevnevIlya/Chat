package com.example.myapplication.data.room

data class CompanionStates(
    val companions: List<Companion> = emptyList(),
    val name: String = "",
    val status: String = "",
    val photoUrl: String = ""
)
