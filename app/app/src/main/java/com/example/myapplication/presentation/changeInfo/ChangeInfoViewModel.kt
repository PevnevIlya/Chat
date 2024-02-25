package com.example.myapplication.presentation.changeInfo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.ChangeInfoServiceImplementation
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.data.room.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

@HiltViewModel
class ChangeInfoViewModel @Inject constructor(
    private val changeInfo: ChangeInfoServiceImplementation,
    private val dao: UserDao
): ViewModel() {
    var user by mutableStateOf(UserModel("", "", ""))
    val loadedBitmap = mutableStateOf<Bitmap?>(null)
    init {
        refreshUserInfo()

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun loadImageFromServer(base64String: String, bitmapState: MutableState<Bitmap?>) {
        GlobalScope.launch(Dispatchers.Main) {
            val byteArray = Base64.decode(base64String, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(byteArray)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmapState.value = bitmap
        }
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
            loadImageFromServer(userResult.photoUrl ?: "empty", loadedBitmap)
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
            Log.d("ServerTest", "user is $user")
            changeInfo.changeUserInfo(user.email, user.name.toString(), user.status.toString(), user.photoUrl.toString())
        }
    }
}