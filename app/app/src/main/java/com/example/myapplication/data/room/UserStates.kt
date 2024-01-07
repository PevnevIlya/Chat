package com.example.myapplication.data.room

import com.example.myapplication.domain.usecases.validate.email.ValidateEmail

data class UserStates(
    val email: String = "",
    val password: String = ""
)
