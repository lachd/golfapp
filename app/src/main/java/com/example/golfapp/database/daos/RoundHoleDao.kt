package com.example.golfapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.golfapp.database.entities.Round
import com.example.golfapp.database.entities.RoundHole

@Dao
interface RoundHoleDao {

    @Query("SELECT * FROM roundhole WHERE round_id = :roundId")
    suspend fun getRoundHolesForRound(roundId: Int): List<RoundHole>

    @Query("SELECT * FROM roundhole WHERE round_id = :roundId AND course_hole_id = :courseHoleId")
    suspend fun getRoundHole(roundId: Int, courseHoleId: Int): RoundHole?

    @Query("SELECT * FROM roundhole where id = :id")
    suspend fun getRoundHoleById(id: Int): RoundHole

    @Upsert
    suspend fun upsertRoundHole(roundHole: RoundHole): Long

    @Delete
    suspend fun deleteRoundHole(roundHole: RoundHole)

    data class RoundHoleWithDetails(
        val id: Int,
        val courseHoleId: Int,
        val roundId: Int,
        val score: Int,
        val holeNumber: Int,
        val par: Int
    )
}