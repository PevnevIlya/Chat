package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileInputStream
import android.util.Base64

fun getPathFromUri(context: Context, uri: Uri): String? {
    var filePath: String? = null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    cursor?.let {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            filePath = it.getString(columnIndex)
        }
        it.close()
    }
    return filePath
}

fun convertFileToByteArray(filePath: String): ByteArray {
    val file = File(filePath)
    val inputStream = FileInputStream(file)
    val byteArray = inputStream.readBytes()
    inputStream.close()
    return byteArray
}

fun byteArrayToBase64(byteArray: ByteArray): String {
    val base64Bytes = Base64.encode(byteArray, Base64.DEFAULT)
    return String(base64Bytes)
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}


enum class MessageType {
    TextMessage,
    PhotoMessage,
    VoiceMessage
}