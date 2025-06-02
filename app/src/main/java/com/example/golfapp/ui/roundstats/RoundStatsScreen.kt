package com.example.golfapp.ui.roundstats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.golfapp.ui.hole.HoleViewModel
import kotlin.math.round

@Composable
fun RoundStatsScreen (
    roundId: Int,
    navController: NavController,
    roundStatsViewModel: RoundStatsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(roundId) {
        roundStatsViewModel.loadData()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "stats!")
        }
    }
}