package com.example.myapplication.data.implementations

import android.util.Log
import com.example.myapplication.data.remote.UserDto
import com.example.myapplication.domain.interfaces.RegistrationService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RegistrationServiceImplementation(
    private val client: HttpClient
): RegistrationService {
    override suspend fun registerNewUser(email: String, password: String): String {
        return try {
            val response = client.post(RegistrationService.Endpoints.Registration.url){
                contentType(ContentType.Application.Json)
                setBody(UserDto(email, password))
            }
            return response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Aboba", e.toString())
            e.toString()
        }
    }
}