package com.example.myapplication.domain.usecases.validate

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
