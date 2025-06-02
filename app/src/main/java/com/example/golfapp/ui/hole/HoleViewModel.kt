package com.example.golfapp.ui.hole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.daos.CourseHoleDao
import com.example.golfapp.database.entities.CourseHole
import com.example.golfapp.database.entities.Round
import com.example.golfapp.database.entities.RoundHole
import com.example.golfapp.repository.CourseHoleRepository
import com.example.golfapp.repository.RoundHoleRepository
import com.example.golfapp.repository.RoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoleViewModel @Inject constructor(
    private val roundRepository: RoundRepository,
    private val courseHoleRepository: CourseHoleRepository,
    private val roundHoleRepository: RoundHoleRepository
): ViewModel() {

    // UI state
    var isLoading by mutableStateOf(true)
        private set

    var currentHoleIndex by mutableStateOf(0)
        private set

    var roundDetails by mutableStateOf<Round?>(null)
        private set

    // Data state
    var roundHoles by mutableStateOf<List<HoleData>>(emptyList())
        private set

    // current hole in UI
    var currentHole: HoleData? = null
        get() = roundHoles.getOrNull(currentHoleIndex)

    fun loadRoundData(roundId: Int) {
        viewModelScope.launch {
            try {
                isLoading = true

                val round = roundRepository.getRoundById(roundId)

                if (round != null) {
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
                                score = roundHole?.score ?: 0,
                                courseHoleId = courseHole.id
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
    val score: Int,
    val courseHoleId: Int
)