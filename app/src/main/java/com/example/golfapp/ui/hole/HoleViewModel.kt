package com.example.golfapp.ui.hole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.entities.Round
import com.example.golfapp.repository.CourseHoleRepository
import com.example.golfapp.repository.RoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoleViewModel @Inject constructor(
    private val roundRepository: RoundRepository,
    private val courseDao: CourseDao
): ViewModel() {

    var courseName by mutableStateOf("")
        private set

    var currentHole by mutableStateOf(1)
        internal set

    var 

    var isLoading by mutableStateOf(true)
        private set

    var score by mutableStateOf(0)
        internal set

    var roundDetails: Round? by mutableStateOf(null)

    fun loadRoundData(roundId: Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                val roundDetails = roundRepository.getCurrentRound()
                if (roundDetails != null) {
                    val course = courseDao.getCourseById(roundDetails.courseId)
                    courseName = course.name
                }

            } catch (e: Exception) {
                courseName = "unknown error"
                println(e)
            } finally {
                isLoading = false
            }

        }
    }
}