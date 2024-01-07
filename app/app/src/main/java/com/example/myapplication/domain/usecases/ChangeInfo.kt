package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.interfaces.ChangeInfoService
import com.example.myapplication.domain.interfaces.LoginService

class ChangeInfo (
    private val changeInfoService: ChangeInfoService
) {
    suspend fun getUserInfo(email: String){
        changeInfoService.getUserInfo(email)
    }

    suspend fun changeUserInfo(email: String, name: String, status: String, photoUrl: String){
        changeInfoService.changeUserInfo(email, name, status, photoUrl)
    }
}