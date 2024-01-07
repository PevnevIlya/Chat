package com.example.myapplication.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class User(
    var password: String,
    @PrimaryKey(autoGenerate = false)
    var email: String
)
