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
import androidx.compose.material3.ButtonDefaults
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
        holeViewModel.loadSettings()
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
            text = holeViewModel.roundDetails?.let { "${it.course.name} - ${it.date}" } ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Button(
            onClick = {
                holeViewModel.saveAndCompleteRound()
                navController.navigate("round_stats/$roundId")
                // TODO - this should go to stats screen
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
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
            HoleDetails(
                hole = hole,
                holeViewModel = holeViewModel
            )
            Scoring(
                hole = hole,
                onScoreChange = { newScore ->
                holeViewModel.updateCurrentHoleScore(newScore)
            })
        }

        NewShot()

        ShotHistory()

        Spacer(modifier = Modifier.weight(1f))
        HoleNavigation(holeViewModel = holeViewModel)
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
                text = "Shot logging coming soon...",
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
                .height(100.dp)
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
fun HoleDetails(
    hole: HoleData,
    holeViewModel: HoleViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ){
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
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                val index = hole.index
                Text(
                    text = "Index: ${index}",
                    style = MaterialTheme.typography.titleMedium,
                )

                val handicapSetting = holeViewModel.settings.find {
                    it.key == "handicap"
                }
                if (handicapSetting != null ) {
                    var strokes = 0
                    if (index <= handicapSetting.value.toInt()) {
                        strokes++
                    }
                    Text(
                        text = "Strokes: ${strokes}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }


            }
        }
    }
}

@Composable
fun Scoring(
    hole: HoleData,
    onScoreChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly //.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {
        Button(
            onClick = {
                if (hole.score > 0) onScoreChange(hole.score - 1)
            },
            enabled = hole.score > 0
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
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Score",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = if (hole.score == 0) "-" else hole.score.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                if (hole.score > 0) {
                    Text(
                        text = when (hole.score - hole.par) {
                            -3 -> "Albatross"
                            -2 -> "Eagle"
                            -1 -> "Birdie"
                            0 -> "Par"
                            1 -> "Bogey"
                            2 -> "D. Bogey"
                            else -> if (hole.score - hole.par > 2) "+${hole.score - hole.par}" else ""
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Button(
            onClick = { onScoreChange(hole.score + 1) }
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Increase score"
            )
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