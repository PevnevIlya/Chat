package com.example.myapplication.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Companion(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var status: String,
    var photoUrl: String
)
