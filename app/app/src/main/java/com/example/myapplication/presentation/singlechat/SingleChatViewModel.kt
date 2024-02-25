package com.example.myapplication.presentation.singlechat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Resource
import com.example.myapplication.data.implementations.ChangeInfoServiceImplementation
import com.example.myapplication.data.models.MessageModel
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.domain.interfaces.SingleChatService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleChatViewModel @Inject constructor(
    private val singleChatService: SingleChatService,
    private val changeInfo: ChangeInfoServiceImplementation
): ViewModel() {
    val messageText = mutableStateOf("")
    var messageList = mutableStateListOf<MessageModel>()
    val state = mutableStateOf(SingleChatState(""))
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()
    var companion by mutableStateOf(UserModel(""))

    fun startActivity(email: String, companionEmail: String) {
        viewModelScope.launch {
            val chatId = viewModelScope.async { getChatId(email = email, companionEmail = companionEmail) }
            val resultId = chatId.await()
            state.value.chatId = chatId.await()
            connectToChat(email, resultId)
            Log.d("qwerty", resultId)
            Log.d("qwerty", state.value.toString())
        }
    }
    private suspend fun getChatId(email: String, companionEmail: String): String {
        return singleChatService.getChatId(email, companionEmail)
    }

    private fun connectToChat(email: String, chatId: String) {
        getAllMessages(chatId)
        viewModelScope.launch {
            val result = singleChatService.initSession(email, chatId)
            when(result) {
                is Resource.Success -> {
                    singleChatService.observeMessages()
                        .onEach { message ->
                            Log.d("ChatTest", "newMessage came $message")
                            messageList.add(message)
                        }.launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    _toastEvent.emit(result.message ?: "Unknown error")
                }
            }
        }
    }
    fun disconnect() {
        viewModelScope.launch {
            singleChatService.closeSession()
        }
    }

    private fun getAllMessages(chatId: String) {
        viewModelScope.launch {
            //state.value.isLoading = true
            val result = async { singleChatService.getAllMessages(chatId) }
            Log.d("qwerty", "$chatId,messages is ${result.await()}")
            val messages = result.await().map { it.toMessage() }
            messageList = messages.toMutableStateList()
        }
    }

    fun sendMessage(email: String, chatId: String) {
        viewModelScope.launch {
            if(messageText.value.isNotBlank()) {
                singleChatService.sendMessage(chatId = chatId, message = messageText.value, senderEmail = email)
            }
        }
    }

    fun onMessageTextChanged(message: String) {
        messageText.value = message
    }
    suspend fun getUserInfo(keyEmail: String): UserModel {
        val result = viewModelScope.async(Dispatchers.IO) {
            changeInfo.getUserInfo(keyEmail)
        }
        return result.await()
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}