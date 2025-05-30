package com.example.golfapp.repository

import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.daos.RoundDao
import com.example.golfapp.database.entities.Round
import com.example.golfapp.util.Converters
import com.example.golfapp.util.Weather
import com.example.golfapp.util.Wind
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.round

@Singleton
class RoundRepository @Inject constructor(
    private val roundDao: RoundDao,
    private val courseDao: CourseDao
){

    fun getAllRounds(): Flow<List<Round>> = roundDao.getAllRounds()

    suspend fun getRoundById(roundId: Int): Round? = roundDao.getRoundById(roundId = roundId)

    suspend fun getCurrentRound(): Round? = roundDao.getCurrentRound()

    suspend fun startNewRound(
        courseName: String,
        weather: Weather,
        wind: Wind
    ): Long {
        val converters = Converters()
        val newRound = Round(
            courseId = courseDao.getCourseByName(courseName).id,
            weather = converters.fromWeather(weather),
            wind = converters.fromWind(wind),
            date = LocalDate.now()
        )
        return roundDao.upsertRound(newRound)
    }

    suspend fun completeRound(roundId: Int, finalScore: Int) {
        roundDao.completeRound(
            roundId = roundId,
            finalScore = finalScore
        )
    }

    suspend fun updateRound(round: Round) = roundDao.upsertRound(round)

    suspend fun deleteRound(round: Round) = roundDao.deleteRound(round)
}