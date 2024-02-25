package com.example.myapplication.presentation.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.implementations.ChangeInfoServiceImplementation
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.data.room.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(
    private val changeInfo: ChangeInfoServiceImplementation,
    private val dao: UserDao
): ViewModel() {
    var user by mutableStateOf(UserModel("", "", ""))
    var companion by mutableStateOf(UserModel("", "", ""))
    val loadedBitmap = mutableStateOf<Bitmap?>(null)

    init {
       refreshUserInfo()
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "Main VM Cleared")
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

    fun refreshUserInfo() {
        viewModelScope.launch(Dispatchers.Main) {
            val emailRes = viewModelScope.async(Dispatchers.IO) {
                dao.getUser()[0].email
            }
            Log.d("ChangeInfo", emailRes.await())
            val userModel = viewModelScope.async(Dispatchers.IO) {
                changeInfo.getUserInfo(emailRes.await())
            }
            val userResult = userModel.await()
            Log.d("ChangeInfo", "user2 is $userResult")
            user.email = emailRes.await()
            user.name = userResult.name
            user.status = userResult.status
            user.photoUrl = userResult.photoUrl
            loadImageFromServer(userResult.photoUrl ?: "empty", loadedBitmap)
            Log.d("ChangeInfo", "userModel2 is $user")
            getCompanionList(emailRes.await())
        }
    }
    suspend fun getUserInfo(keyEmail: String): UserModel {
        val result = viewModelScope.async(Dispatchers.IO) {
            changeInfo.getUserInfo(keyEmail)
        }
        return result.await()
    }

    private fun getCompanionList(email: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val string = viewModelScope.async(Dispatchers.IO) {
                changeInfo.getCompanionList(email)
            }
            val stringResult = string.await()
            Log.d("ChangeUserInfoBody", "stringResult = $stringResult")
            val gson = Gson()
            val listResult = gson.fromJson(stringResult, ListResponse::class.java)
            Log.d("ChangeUserInfoBody", "listResult = $listResult")
            user.companionsList = listResult.list
        }
    }

    var goToChangeInfoActivity by mutableStateOf(false)

    var goToAddCompanionActivity by mutableStateOf(false)

    var goToSingleChat by mutableStateOf("null")

    var selectedItemIndex by mutableIntStateOf(0)
}
data class ListResponse(val list: MutableList<String>)