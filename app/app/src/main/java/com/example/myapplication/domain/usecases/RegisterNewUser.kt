package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.interfaces.RegistrationService

class RegisterNewUser(
    private val registrationService: RegistrationService
) {
    suspend fun execute(email: String, password: String){
        registrationService.registerNewUser(email, password)
    }
}