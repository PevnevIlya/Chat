package com.example.myapplication.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.LoginServiceImplementation
import com.example.myapplication.domain.usecases.validate.email.ValidateEmail
import com.example.myapplication.domain.usecases.validate.password.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginService: LoginServiceImplementation,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
): ViewModel() {
    var state by mutableStateOf(LoginFormState())

    var isLoading by mutableStateOf(false)

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        Log.d("ViewModel", "Login VM Created")
        isLoading = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "Login VM Cleared")
    }

    fun onEvent(event: LoginFormEvent) {
        when(event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.isSuccessful }

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            val webAnswer = viewModelScope.async { onLoginButtonClicked() }
            val answer: String = webAnswer.await()
            Log.d("Server", answer)
            if (answer != "Success") {
                validationEventChannel.send(LoginViewModel.ValidationEvent.Fail)
            } else {
                validationEventChannel.send(LoginViewModel.ValidationEvent.Success)
            }
        }
    }

    private suspend fun onLoginButtonClicked(): String {
        isLoading = true
        try {
            val value = viewModelScope.async {
                loginService.login(state.email, state.password)
            }
            return value.await()
        } finally {
            isLoading = false
        }
    }

    sealed class ValidationEvent {
        object Fail: ValidationEvent()
        object Success: ValidationEvent()
    }
}