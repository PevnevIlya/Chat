package com.example.myapplication.data.implementations

import com.example.myapplication.data.remote.UserDto
import com.example.myapplication.domain.interfaces.LoginService
import com.example.myapplication.domain.interfaces.RegistrationService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoginServiceImplementation(
    private val client: HttpClient
): LoginService {
    override suspend fun login(email: String, password: String): String {
        return try {
            val response = client.post(LoginService.Endpoints.Login.url){
                contentType(ContentType.Application.Json)
                setBody(UserDto(email, password))
            }
            return response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            e.toString()
        }
    }
}