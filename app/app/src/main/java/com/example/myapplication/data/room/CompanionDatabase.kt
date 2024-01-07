package com.example.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Companion::class],
    version = 1
)
abstract class CompanionDatabase: RoomDatabase() {
    abstract val dao: CompanionDao
}