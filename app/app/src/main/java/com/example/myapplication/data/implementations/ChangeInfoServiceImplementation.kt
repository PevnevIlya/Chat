package com.example.myapplication.data.implementations

import com.example.myapplication.data.remote.ChangeDto
import com.example.myapplication.data.remote.UserDto
import com.example.myapplication.domain.interfaces.ChangeInfoService
import com.example.myapplication.domain.interfaces.LoginService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ChangeInfoServiceImplementation(
    private val client: HttpClient
): ChangeInfoService {
    override suspend fun changeUserInfo(name: String, status: String, photoUrl: String): String {
        return try {
            val response = client.post(ChangeInfoService.Endpoints.ChangeUserInfo.url){
                contentType(ContentType.Application.Json)
                setBody(ChangeDto("", name, status, photoUrl))
            }
            return response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
        }
    }

    override suspend fun getUserInfo(email: String): List<String> {
        TODO("Not yet implemented")
    }
}