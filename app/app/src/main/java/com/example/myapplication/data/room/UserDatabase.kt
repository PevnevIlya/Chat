package com.example.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Inject

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract val dao: UserDao
}