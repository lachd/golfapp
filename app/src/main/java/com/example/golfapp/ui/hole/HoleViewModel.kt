package com.example.golfapp.ui.hole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.daos.CourseHoleDao
import com.example.golfapp.database.daos.SettingsDao
import com.example.golfapp.database.entities.Course
import com.example.golfapp.database.entities.CourseHole
import com.example.golfapp.database.entities.Round
import com.example.golfapp.database.entities.RoundHole
import com.example.golfapp.database.entities.Setting
import com.example.golfapp.repository.CourseHoleRepository
import com.example.golfapp.repository.RoundHoleRepository
import com.example.golfapp.repository.RoundRepository
import com.example.golfapp.util.Weather
import com.example.golfapp.util.Wind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HoleViewModel @Inject constructor(
    private val roundRepository: RoundRepository,
    private val courseHoleRepository: CourseHoleRepository,
    private val roundHoleRepository: RoundHoleRepository,
    private val courseDao: CourseDao,
    private val settingsDao: SettingsDao
): ViewModel() {

    // UI state
    var isLoading by mutableStateOf(true)
        private set

    var currentHoleIndex by mutableStateOf(0)
        private set

    var roundDetails by mutableStateOf<EnhancedRoundData?>(null)
        private set

    // Data state
    var roundHoles by mutableStateOf<List<HoleData>>(emptyList())
        private set

    var settings by mutableStateOf<List<Setting>>(emptyList())
        private set

    // current hole in UI
    var currentHole: HoleData? = null
        get() = roundHoles.getOrNull(currentHoleIndex)

    fun loadSettings() {
        viewModelScope.launch {
            try {
                settings = settingsDao.getSettings()
            } catch (e: Exception) {
                println("Error loading settings: $e")
            }
        }
    }

    fun loadRoundData(roundId: Int) {
        viewModelScope.launch {
            try {
                isLoading = true

                val round = roundRepository.getRoundById(roundId)

                if (round != null) {
                    roundDetails = EnhancedRoundData(
                        id = round.id,
                        course = courseDao.getCourseById(round.courseId),
                        weather = round.weather,
                        wind = round.wind,
                        date = round.date,
                        finalScore = round.finalScore,
                        isActive = round.isActive
                    )

                    val courseHoles =
                        courseHoleRepository.getAllCourseHolesForCourse(round.courseId)

                    if (courseHoles != null) {
                        roundHoleRepository.initializeRoundHoles(
                            roundId = roundId,
                            courseHoles = courseHoles
                        )
                        val roundHolesData = roundHoleRepository.getRoundHolesForRound(roundId)
                        // Convert to UI-friendly format by combining with course hole data
                        roundHoles = courseHoles.map { courseHole ->
                            val roundHole = roundHolesData.find { it.courseHoleId == courseHole.id }
                            HoleData(
                                roundHoleId = roundHole?.id ?: 0,
                                holeNumber = courseHole.holeNumber,
                                par = courseHole.par,
                                score = roundHole?.score ?: courseHole.par,
                                courseHoleId = courseHole.id,
                                index = courseHole.index
                            )
                        }.sortedBy { it.holeNumber }
                    }
                } else {
                    // should throw an exception here
                }


            } catch (e: Exception) {
                println("Error loading round data: $e")
            } finally {
                isLoading = false
            }

        }
    }

    fun navigateToHole(holeIndex: Int) {
        if (holeIndex in 0 until roundHoles.size) {
            saveCurrentHole()
            currentHoleIndex = holeIndex
        }
    }

    fun navigateNext() {
        if (currentHoleIndex < roundHoles.size - 1) {
            navigateToHole(currentHoleIndex + 1)
        }
    }

    fun navigatePrevious() {
        if (currentHoleIndex > 0) {
            navigateToHole(currentHoleIndex - 1)
        }
    }

    fun updateCurrentHoleScore(newScore: Int) {
        val currentHoleData = currentHole ?: return
        val updatedHoles = roundHoles.toMutableList()
        updatedHoles[currentHoleIndex] = currentHoleData.copy(score = newScore)
        roundHoles = updatedHoles
    }

    private fun saveCurrentHole() {
        val currentHoleData = currentHole ?: return
        val roundId = roundDetails?.id ?: return

        viewModelScope.launch {

            try {
                val roundHole = RoundHole(
                    id = currentHoleData.roundHoleId,
                    courseHoleId = currentHoleData.courseHoleId,
                    roundId = roundId,
                    score = currentHoleData.score
                )
                roundHoleRepository.saveRoundHole(roundHole)
            } catch (e: Exception) {
                println("Error saving hole: $e")
            }
        }
    }

    fun saveAndCompleteRound() {
        saveCurrentHole()

        viewModelScope.launch {
            try {
                val roundId = roundDetails?.id ?: return@launch
                val totalScore = roundHoles.sumOf { it.score }

                roundRepository.completeRound(
                    roundId = roundId,
                    finalScore = totalScore
                )
            } catch (e: Exception) {
                println("Error saving hole: $e")
            }
        }
    }

    fun saveRound() {
        saveCurrentHole() // Just save current state, don't complete
    }
}

data class HoleData(
    val roundHoleId: Int,
    val holeNumber: Int,
    val par: Int,
    val index: Int,
    val score: Int,
    val courseHoleId: Int
)

data class EnhancedRoundData(
    val id: Int,
    val course: Course,
    val weather: String,
    val wind: String,
    val date: LocalDate,
    val finalScore: Int,
    val isActive: Boolean,
)