package com.example.myapplication.presentation.addcompanion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.ChangeInfoServiceImplementation
import com.example.myapplication.data.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCompanionViewModel @Inject constructor(
    private val changeInfo: ChangeInfoServiceImplementation,
    private val dao: UserDao
): ViewModel() {
    var isLoading by mutableStateOf(false)
    var needToGoBack by mutableStateOf(false)
    var editValue by mutableStateOf("")

    fun onConfirmButtonClicked() {
        isLoading = true
        viewModelScope.launch(Dispatchers.Main) {
            val emailRes = viewModelScope.async(Dispatchers.IO) {
                dao.getUser()[0].email
            }
            val result = viewModelScope.async(Dispatchers.IO) {
                changeInfo.addCompanion(emailRes.await(), editValue)
            }
            if(result.await() == "OK"){
                isLoading = false
                needToGoBack = true
            } else {
                isLoading = false
            }
        }
    }
}