package com.example.myapplication.domain.usecases.validate.password

import android.util.Patterns
import com.example.myapplication.domain.usecases.validate.ValidationResult

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if(password.length < 8){
            return ValidationResult(
                false,
                "The password must be 8+ symbols!"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } && password.any{ it.isLetter() }
        if(!containsLettersAndDigits){
            return ValidationResult(
                false,
                "There need to be digits and letters in password!"
            )
        }
        return ValidationResult(true)
    }
}