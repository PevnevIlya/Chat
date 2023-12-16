package com.example.myapplication.domain.usecases.validate.email

import android.util.Patterns
import com.example.myapplication.domain.usecases.validate.ValidationResult

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                false,
                "The email can't be blank!"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                false,
                "The email isn't valid!"
            )
        }
        return ValidationResult(true)
    }
}