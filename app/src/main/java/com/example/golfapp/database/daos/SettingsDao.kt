package com.example.golfapp.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.golfapp.database.entities.Setting

@Dao
interface SettingsDao {

    @Query("SELECT * FROM setting")
    suspend fun getSettings(): List<Setting>

    @Upsert
    suspend fun upsertSetting(setting: Setting)
}