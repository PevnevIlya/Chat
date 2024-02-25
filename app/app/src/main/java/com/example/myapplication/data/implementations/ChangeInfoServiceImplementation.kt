package com.example.myapplication.data.implementations

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.myapplication.byteArrayToBase64
import com.example.myapplication.convertFileToByteArray
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.data.remote.AddCompanionDto
import com.example.myapplication.data.remote.ChangeDto
import com.example.myapplication.data.remote.SendEmailDto
import com.example.myapplication.domain.interfaces.ChangeInfoService
import com.example.myapplication.getPathFromUri
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ChangeInfoServiceImplementation(
    private val context: Context,
    private val client: HttpClient
): ChangeInfoService {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun changeUserInfo(email: String, name: String, status: String, photoUrl: String) {
        try {
            val path = getPathFromUri(context, photoUrl.toUri())
            Log.d("NewUri", "Path $path")
            val file = convertFileToByteArray(path.toString())
            Log.d("NewUri", "File ${file.toString()}")
            val res = byteArrayToBase64(file)
            Log.d("NewUri", "Res $res")
            val response = client.post(ChangeInfoService.Endpoints.ChangeUserInfo.url){
                contentType(ContentType.Application.Json)
                setBody(ChangeDto(email, name, status, res))
            }
            Log.d("ServerTest", response.toString())
            Log.d("ServerTest", response.body())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getUserInfo(email: String): UserModel {
        return try {
            val response = client.post(ChangeInfoService.Endpoints.GetUserInfo.url){
                contentType(ContentType.Application.Json)
                setBody(SendEmailDto(email))
            }
            Log.d("Server", response.toString())
            Log.d("Server", response.body())
            val gson = Gson()
            val userJson: String = response.body()
            val user = gson.fromJson(userJson, UserModel::class.java)
            Log.d("Server", "user = $user")
            user
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
            UserModel("", "", "")
        }
    }

    override suspend fun addCompanion(email: String, companionEmail: String): String {
        return try {
            val response = client.post(ChangeInfoService.Endpoints.AddCompanion.url){
                contentType(ContentType.Application.Json)
                setBody(AddCompanionDto(email, companionEmail))
            }
            Log.d("Server", response.toString())
            Log.d("Server", response.body())
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
            "Error"
        }
    }

    override suspend fun getCompanionList(email: String): String {
        return try {
            val response = client.post(ChangeInfoService.Endpoints.GetCompanionList.url){
                contentType(ContentType.Application.Json)
                setBody(SendEmailDto(email))
            }
            Log.d("ChangeUserInfo", response.toString())
            Log.d("ChangeUserInfoBody", response.body())
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
            "Error"
        }
    }
}