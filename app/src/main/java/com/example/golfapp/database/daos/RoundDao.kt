package com.example.golfapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.golfapp.database.entities.Round
import kotlinx.coroutines.flow.Flow

@Dao
interface RoundDao {

    @Query("SELECT * FROM round")
    fun getAllRounds(): Flow<List<Round>>

    @Query("SELECT * FROM round WHERE id = :roundId")
    suspend fun getRoundById(roundId: Int): Round?

    // note: only one round should ever be active at one time.
    //  business logic should make sure of this?
    @Query("SELECT * FROM round WHERE is_active = true LIMIT 1")
    suspend fun getCurrentRound(): Round?

    @Upsert
    suspend fun upsertRound(round: Round): Long

    @Delete
    suspend fun deleteRound(round: Round)

    @Query("UPDATE round SET is_active = false, final_score = :finalScore WHERE id = :roundId")
    suspend fun completeRound(roundId: Int, finalScore: Int)
}