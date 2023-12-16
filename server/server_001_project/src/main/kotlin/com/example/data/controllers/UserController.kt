package com.example.data.controllers

import com.example.data.model.JSONmodels.ChangeInfoReceiveRemote
import com.example.data.model.JSONmodels.ChangeInfoResponseRemote
import com.example.data.model.JSONmodels.ReceiveRemote
import com.example.data.model.UserModel
import com.example.utils.USERS_COLLECTION
import com.example.utils.validators.isValidEmail
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.util.UUID

class UserController(
    private val db: CoroutineDatabase
) {
    suspend fun registerNewUser(call: ApplicationCall) {
        val usersCollection = db.getCollection<UserModel>(USERS_COLLECTION)
        val receive = call.receive<ReceiveRemote>()

        if (!receive.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            return
        }

        val existingUser = usersCollection.findOne(UserModel::email eq receive.email)
        if (existingUser != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
            return
        }

        val id = UUID.randomUUID().toString()

        val newUser = UserModel(
            id = id,
            username = "",
            password = receive.password,
            email = receive.email
        )

        usersCollection.insertOne(newUser)

        call.respond(HttpStatusCode.Created, "Success")
    }

    suspend fun loginUser(call: ApplicationCall) {
        val receive = call.receive<ReceiveRemote>()
        val collection = db.getCollection<UserModel>(USERS_COLLECTION)
        val user = collection.findOne(UserModel::email eq receive.email)

        if(user != null && user.password == receive.password) {
            call.respond(HttpStatusCode.OK, "Success")
        } else {
            call.respond(HttpStatusCode.OK, "Fail")
        }
    }

    suspend fun changeUserInfo(call: ApplicationCall){
        val receive = call.receive<ChangeInfoReceiveRemote>()
        val collection = db.getCollection<UserModel>(USERS_COLLECTION)
        val user = collection.findOne(UserModel::email eq receive.email)

        if(user != null) {
            user.username = receive.username
            user.status = receive.status
            user.photoUrl = receive.photoUrl

            collection.updateOne(UserModel::email eq receive.email, user)
            call.respond(HttpStatusCode.OK, "User updated")
        } else {
            call.respond(HttpStatusCode.NotFound, "User not found")
        }
    }

    suspend fun getUserInfo(call: ApplicationCall){
        val receive = call.receive<ChangeInfoReceiveRemote>()
        val collection = db.getCollection<UserModel>(USERS_COLLECTION)
        val user = collection.findOne(UserModel::email eq receive.email)

        if(user != null) {
            val result = ChangeInfoResponseRemote(user.username, user.status, user.photoUrl)
            call.respond(HttpStatusCode.OK, result)
        } else {
            call.respond(HttpStatusCode.NotFound, "User not found")
        }
    }
}
