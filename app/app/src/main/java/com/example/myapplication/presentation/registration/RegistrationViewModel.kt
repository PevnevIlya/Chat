package com.example.myapplication.presentation.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.RegistrationServiceImplementation
import com.example.myapplication.data.room.User
import com.example.myapplication.data.room.UserDao
import com.example.myapplication.data.room.UserStates
import com.example.myapplication.domain.usecases.validate.ValidationResult
import com.example.myapplication.domain.usecases.validate.email.ValidateEmail
import com.example.myapplication.domain.usecases.validate.password.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationService: RegistrationServiceImplementation,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val dao: UserDao
): ViewModel() {

    var validationState by mutableStateOf(RegistrationFormState())

    var isLoading by mutableStateOf(true)

    var isRegistered by mutableStateOf(false)

    init {

        viewModelScope.launch {
            val user = viewModelScope.async(Dispatchers.IO) {
                dao.getUser()
            }
            val result = user.await()
            Log.d("Test", result.toString())
            if(result.isEmpty()) {
                isLoading = false
            } else {
                isRegistered = true
            }
        }
        Log.d("ViewModel", "Registration VM Created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "Registration VM Cleared")
    }

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onValidationEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                validationState = validationState.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                validationState = validationState.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private suspend fun saveUser() {
        val email = validationState.email
        val password = validationState.password
        dao.upsertUser(User(email = email, password = password))
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(validationState.email)
        val passwordResult = validatePassword.execute(validationState.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.isSuccessful }

        if(hasError) {
            validationState = validationState.copy(
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
                saveUser()
            }
        }
    }

    private suspend fun onRegisterButtonClicked(): String {
        isLoading = true
        try {
            val value = viewModelScope.async {
                registrationService.registerNewUser(validationState.email, validationState.password)
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