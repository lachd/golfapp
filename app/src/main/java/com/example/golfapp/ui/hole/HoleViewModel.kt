package com.example.golfapp.ui.hole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfapp.database.entities.Round
import com.example.golfapp.repository.CourseHoleRepository
import com.example.golfapp.repository.RoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoleViewModel @Inject constructor(
    private val roundRepository: RoundRepository,
    private val courseHoleRepository: CourseHoleRepository
): ViewModel() {

    

    var score by mutableStateOf(0)
        internal set

    var roundDetails: Round? by mutableStateOf(null)

    fun getRoundDetails(roundId: Int) {
        viewModelScope.launch {
            roundDetails = roundRepository.getRoundById(roundId)
        }
    }

    fun getCourseHoleDetails(courseId: Int, holeNumber: Int) {
        viewModelScope.launch {
            courseHoleRepository.getCourseHoleFromCourseByNumber(courseId, holeNumber)
        }
    }

}