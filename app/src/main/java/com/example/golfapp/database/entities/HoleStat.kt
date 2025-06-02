package com.example.golfapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.golfapp.util.Converters
import com.example.golfapp.util.Stat

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
@TypeConverters(Converters::class)
data class HoleStat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "round_hole_id") val roundHoleId: Int,
    val type: Stat,
    val value: Int,
)