package com.example.golfapp.ui.newround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfapp.repository.RoundRepository
import com.example.golfapp.util.Weather
import com.example.golfapp.util.Wind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewRoundViewModel @Inject constructor(
    private val repository: RoundRepository
): ViewModel() {

    private val courseList = listOf<String>("Moruya", "Narooma", "Mollymook", "Catalina")

    var selectedCourse by mutableStateOf(courseList[0])
        private set

    var selectedWeather by mutableStateOf(Weather.SUNNY)
        private set

    var selectedWind by mutableStateOf(Wind.CALM)
        private set

    var isStartingRound by mutableStateOf(false)
        private set

    val courses: List<String> = courseList

    fun selectCourse(course: String) {
        selectedCourse = course
    }

    fun selectWeather(weather: Weather) {
        selectedWeather = weather
    }

    fun selectWind(wind: Wind) {
        selectedWind = wind
    }

    fun startNewRound(onRoundStarted: (Int) -> Unit) {
        viewModelScope.launch {
            isStartingRound = true
            try {
                val roundId = repository.startNewRound(
                    courseName = selectedCourse,
                    weather = selectedWeather,
                    wind = selectedWind
                )
                onRoundStarted(roundId.toInt())
            } catch (e: Exception) {
                // todo - display an error dialog
            } finally {
                isStartingRound = false
            }
        }
    }

    fun getRoundData(): RoundData {
        return RoundData(
            selectedCourse, selectedWeather, selectedWind
        )
    }

    fun resetSelections() {
        selectedCourse = courseList[0]
        selectedWeather = Weather.SUNNY
        selectedWind = Wind.CALM
    }

    fun checkForActiveRound(onActiveRoundFound: (Int) -> Unit) {
        viewModelScope.launch {
            val activeRound = repository.getCurrentRound()
            activeRound?.let { round ->
                onActiveRoundFound(round.id)
            }
        }
    }
}

// Data class to represent the complete round setup
data class RoundData(
    val course: String,
    val weather: Weather,
    val wind: Wind
)