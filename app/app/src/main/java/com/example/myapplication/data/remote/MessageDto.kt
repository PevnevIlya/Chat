package com.example.myapplication.data.remote

import com.example.myapplication.MessageType
import com.example.myapplication.data.models.MessageModel
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDto(
    val text: String,
    val timestamp: Long,
    val email: String,
    val id: String
) {
    fun toMessage(): MessageModel {
        val date = Date(timestamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return MessageModel(
            text = text,
            formattedTime = formattedDate,
            email = email
        )
    }
}