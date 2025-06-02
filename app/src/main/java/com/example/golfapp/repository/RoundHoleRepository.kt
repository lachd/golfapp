package com.example.golfapp.repository

import com.example.golfapp.database.daos.RoundHoleDao
import com.example.golfapp.database.daos.RoundHoleDao.RoundHoleWithDetails
import com.example.golfapp.database.entities.CourseHole
import com.example.golfapp.database.entities.Round
import com.example.golfapp.database.entities.RoundHole
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoundHoleRepository @Inject constructor(
    private val roundHoleDao: RoundHoleDao
) {

    suspend fun getRoundHolesForRound(roundId: Int): List<RoundHole> =
        roundHoleDao.getRoundHolesForRound(roundId)


    suspend fun getRoundHole(roundId: Int, courseHoleId: Int) {
        roundHoleDao.getRoundHole(
            roundId = roundId,
            courseHoleId = courseHoleId
        )
    }

    suspend fun saveRoundHole(roundHole: RoundHole) {
        roundHoleDao.upsertRoundHole(roundHole)
    }

    suspend fun initializeRoundHoles(roundId: Int, courseHoles: List<CourseHole>) {
        courseHoles.forEach {
            val existingRoundHole = getRoundHole(roundId, it.id)
            if (existingRoundHole == null) {
                saveRoundHole(
                    RoundHole(
                        courseHoleId = it.id,
                        roundId = roundId,
                        score = 0 // 0 indicates not yet scored
                    )
                )
            }
        }
    }
}