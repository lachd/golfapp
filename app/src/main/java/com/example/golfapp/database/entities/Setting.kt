package com.example.golfapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setting(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "setting_key") val key: String,
    @ColumnInfo(name = "setting_value") val value: String
)