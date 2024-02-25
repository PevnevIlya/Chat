package com.example.myapplication.presentation.changeInfo

import android.net.Uri
import com.example.myapplication.presentation.registration.RegistrationFormEvent

sealed class ChangeInfoEvent {
    data class NameChanged(val name: String): ChangeInfoEvent()
    data class StatusChanged(val status: String): ChangeInfoEvent()
    data class PhotoUrlChanged(val photoUrl: String?): ChangeInfoEvent()

    object Submit: ChangeInfoEvent()
}