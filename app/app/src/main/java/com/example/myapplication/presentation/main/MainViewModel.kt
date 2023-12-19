package com.example.myapplication.presentation.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel  @Inject constructor(

): ViewModel() {

    init {
        Log.d("ViewModel", "Main VM Created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel", "Main VM Cleared")
    }

    var goToChangeInfoActivity by mutableStateOf(false)

    var selectedItemIndex by mutableIntStateOf(0)
}