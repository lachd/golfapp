package com.example.golfapp.ui.hole

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HoleScreen(
    roundId: Int,
    navController: NavController,
    holeViewModel: HoleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(roundId) {
        holeViewModel.loadRoundData(roundId)
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            TopBar(
                roundId = roundId,
                navController = navController,
                holeViewModel = holeViewModel
            )

            if (holeViewModel.isLoading) {
                LoadingContent()
            } else {
                HoleContent(holeViewModel = holeViewModel)
            }

        }
    }
}

@Composable
private fun TopBar(
    roundId: Int,
    navController: NavController,
    holeViewModel: HoleViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go back",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    holeViewModel.saveRound() // Save before leaving
                    navController.navigate("new_round")
                }
        )

        Text(
            text = holeViewModel.roundDetails?.let { "Round at ${it.}" } ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Button(
            onClick = {
                holeViewModel.saveAndCompleteRound()
                navController.navigate("round_stats/$roundId")
                // TODO - this should go to stats screen
            }
        ) {
            Text("Finish Round")
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading round...",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun HoleContent(holeViewModel: HoleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        holeViewModel.currentHole?.let { hole ->

            HoleDetails(hole = hole)

            ScoreSelector(
                currentScore = hole.score,
                par = hole.par,
                onScoreChange = { newScore ->
                    holeViewModel.updateCurrentHoleScore(newScore)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        NewShot()

        Spacer(modifier = Modifier.height(16.dp))
        ShotHistory()

        Spacer(modifier = Modifier.height(16.dp))
        HoleNavigation(holeViewModel = holeViewModel)
    }
}

@Composable
private fun HoleDetails(hole: HoleData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hole ${hole.holeNumber}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Par ${hole.par}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun NewShot(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Shot tracking coming soon...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun ShotHistory(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Shot history coming soon...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun ScoreSelector(
    currentScore: Int,
    par: Int,
    onScoreChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Score",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (currentScore > 0) onScoreChange(currentScore - 1)
                },
                enabled = currentScore > 0
            ) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = "Decrease score"
                )
            }

            Box(
                modifier = Modifier
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (currentScore == 0) "-" else currentScore.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    if (currentScore > 0) {
                        Text(
                            text = when (currentScore - par) {
                                -3 -> "Albatross"
                                -2 -> "Eagle"
                                -1 -> "Birdie"
                                0 -> "Par"
                                1 -> "Bogey"
                                2 -> "Double Bogey"
                                else -> if (currentScore - par > 2) "+${currentScore - par}" else ""
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Button(
                onClick = { onScoreChange(currentScore + 1) }
            ) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Increase score"
                )
            }
        }
    }
}

@Composable
private fun HoleNavigation(holeViewModel: HoleViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { holeViewModel.navigatePrevious() },
            enabled = holeViewModel.currentHoleIndex > 0
        ) {
            Text("Previous")
        }

        Box(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hole ${holeViewModel.currentHole?.holeNumber ?: 1}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Button(
            onClick = { holeViewModel.navigateNext() },
            enabled = holeViewModel.currentHoleIndex < holeViewModel.roundHoles.size - 1
        ) {
            Text("Next")
        }
    }
}