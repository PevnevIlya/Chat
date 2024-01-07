package com.example.myapplication.data.implementations

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.myapplication.data.models.UserModel
import com.example.myapplication.data.remote.AddCompanionDto
import com.example.myapplication.data.remote.ChangeDto
import com.example.myapplication.data.remote.GetDto
import com.example.myapplication.data.remote.SendEmailDto
import com.example.myapplication.domain.interfaces.ChangeInfoService
import com.example.myapplication.getBitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.io.ByteArrayOutputStream

class ChangeInfoServiceImplementation(
    private val context: Context,
    private val client: HttpClient
): ChangeInfoService {
    override suspend fun changeUserInfo(email: String, name: String, status: String, photoUrl: String) {
        try {
            val photo = getBitmap(context, photoUrl)
            Log.d("Bitmap", photo.toString())

            val outputStream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val imageBytes = outputStream.toByteArray().toString()
            Log.d("Bitmap", imageBytes)
            val response = client.post(ChangeInfoService.Endpoints.ChangeUserInfo.url){
                contentType(ContentType.Application.Json)
                setBody(ChangeDto(email, name, status, imageBytes))
            }
            Log.d("Server", response.toString())
            Log.d("Server", response.body())
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