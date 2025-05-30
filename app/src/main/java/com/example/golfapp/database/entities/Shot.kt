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
        ),
        ForeignKey(
            entity = Club::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("club_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Shot(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "round_hole_id") val roundHoleId: Int,
    @ColumnInfo(name = "club_id") val clubId: Int,
    @ColumnInfo(name = "lateral_dispersion") val lateralDispersion: Int,
    @ColumnInfo(name = "longitudinal_dispersion") val longitudinalDispersion: Int,
    @ColumnInfo(name = "start_line") val startLine: String,
    val curve: String,
    @ColumnInfo(name = "lie_type") val lieType: String,
    @ColumnInfo(name = "place_in_sequence") val placeInSequence: String,
)