package com.example.golfapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.golfapp.util.Converters
import java.time.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("course_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@TypeConverters(Converters::class)
data class Round(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "course_id") val courseId: Int,
    val weather: String,
    val wind: String,
    val date: LocalDate,
    @ColumnInfo(name = "final_score") val finalScore: Int = -1,
    @ColumnInfo(name = "is_active") val isActive: Boolean = false
)