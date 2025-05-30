package com.example.golfapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoundHole::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("round_hole_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HoleStat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "round_hole_id") val roundHoleId: Int,
    val type: Enum<Stat>,
    val value: Int,
)