package com.example.myapplication.presentation.registration

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.RegistrationServiceImplementation
import com.example.myapplication.domain.usecases.validate.email.ValidateEmail
import com.example.myapplication.domain.usecases.validate.password.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationService: RegistrationServiceImplementation,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
): ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    var isLoading by mutableStateOf(false)

    init {
        Log.d("ViewModel", "Registration VM Created")
        isLoading = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "Registration VM Cleared")
    }

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit -> {
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
            val webAnswer = async { onRegisterButtonClicked() }
            val answer: String = webAnswer.await()
            Log.d("Server", answer)
            if (answer == "User already exists") {
                validationEventChannel.send(ValidationEvent.UserExists)
            } else if (answer != "Success") {
                validationEventChannel.send(ValidationEvent.Fail)
            } else {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    private suspend fun onRegisterButtonClicked(): String {
        isLoading = true
        try {
            val value = viewModelScope.async {
                registrationService.registerNewUser(state.email, state.password)
            }
            return value.await()
        } finally {
            isLoading = false
        }
    }

    sealed class ValidationEvent {
        object Fail: ValidationEvent()
        object Success: ValidationEvent()
        object UserExists: ValidationEvent()
    }
}