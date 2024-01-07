package com.example.myapplication.presentation.changeInfo

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.ChangeInfoServiceImplementation
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.data.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class ChangeInfoViewModel @Inject constructor(
    private val changeInfo: ChangeInfoServiceImplementation,
    private val dao: UserDao
): ViewModel() {
    var user by mutableStateOf(UserModel("", "", ""))
    init {
        refreshUserInfo()
    }

    fun refreshUserInfo(){
        viewModelScope.launch(Dispatchers.Main) {
            val emailRes = viewModelScope.async(Dispatchers.IO) {
                dao.getUser()[0].email
            }
            Log.d("ChangeInfo", emailRes.await())
            val userModel = viewModelScope.async(Dispatchers.IO) {
                changeInfo.getUserInfo(emailRes.await())
            }
            val userResult = userModel.await()
            Log.d("ChangeInfo", "user is $userResult")
            user.email = emailRes.await()
            user.name = userResult.name
            user.status = userResult.status
            user.photoUrl = userResult.photoUrl
            Log.d("ChangeInfo", "userModel is $user")
        }
    }

    fun onValueChanged(event: ChangeInfoEvent){
        when (event) {
            is ChangeInfoEvent.NameChanged -> {
                viewModelScope.launch(Dispatchers.Main) {
                    user = user.copy(
                        name = event.name,
                        status = user.status,
                        photoUrl = user.photoUrl
                    )
                }
            }
            is ChangeInfoEvent.PhotoUrlChanged -> {
                viewModelScope.launch(Dispatchers.Main) {
                    user = user.copy(
                        name = user.name,
                        status = user.status,
                        photoUrl = event.photoUrl
                    )
                }
            }
            is ChangeInfoEvent.StatusChanged -> {
                viewModelScope.launch(Dispatchers.Main) {
                    user = user.copy(
                        name = user.name,
                        status = event.status,
                        photoUrl = user.photoUrl
                    )
                }
            }
            ChangeInfoEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Server", "user is $user")
            changeInfo.changeUserInfo(user.email, user.name.toString(), user.status.toString(), user.photoUrl.toString())
        }
    }
}