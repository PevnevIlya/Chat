package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.interfaces.LoginService
import com.example.myapplication.domain.interfaces.RegistrationService

class Login(
    private val loginService: LoginService
) {
    suspend fun execute(email: String, password: String){
        loginService.login(email, password)
    }
}