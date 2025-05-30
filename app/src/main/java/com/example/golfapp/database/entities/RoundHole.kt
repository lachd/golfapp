package com.example.golfapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CourseHole::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("course_hole_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Round::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("round_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RoundHole(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "course_hole_id") val courseHoleId: Int,
    @ColumnInfo(name = "round_id") val roundId: Int,
    val score: Int,
)