package com.example.myapplication.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface CompanionDao {
    @Upsert
    suspend fun upsertCompanion(companion: Companion)
    @Delete
    suspend fun deleteCompanion(companion: Companion)
    @Query("SELECT * FROM companion")
    fun getCompanionList(): Flow<List<Companion>>
}