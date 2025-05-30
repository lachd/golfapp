package com.example.golfapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val key: String,
    val value: String
)