package com.example.golfapp.ui.hole

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
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
        holeViewModel.score = holeViewModel.
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            if (holeViewModel.isLoading) {
                CircularProgressIndicator()
                Text(
                    text = "Loading round...",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            } else {
                
                HoleNavigation(
                    holeViewModel = holeViewModel
                )
                
                Text("current roundId = ${roundId}")
                Text("playing course: ${holeViewModel.courseName}")


                Spacer(modifier.height(16.dp))
                StartShot(modifier = modifier)

                Spacer(modifier.height(16.dp))
                ShotHistory(modifier = modifier)

                Spacer(modifier.height(16.dp))
                ScoreSelector(
                    holeViewModel,
                    modifier = modifier
                )
                
            }
        }
    }
}

@Composable
fun StartShot(modifier: Modifier = Modifier) {
    Text(text = "placeholder for starting a shot")
}

@Composable
fun ShotHistory(modifier: Modifier = Modifier) {
    Text(text = "placeholder for shot history")
}

@Composable
fun ScoreSelector(
    holeViewModel: HoleViewModel,
    modifier: Modifier = Modifier
) {
    Text(text = "placeholder for setting the score")
    Row {
        Button(
            onClick = {
                holeViewModel.score--
            },
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Box(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = holeViewModel.score.toString(),
                modifier = Modifier.padding(16.dp)
            )
        }
        Button(
            onClick = {
                holeViewModel.score++
            },
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun HoleNavigation(
    holeViewModel: HoleViewModel,
    modifier: Modifier = Modifier
) {
    Row {
        if (holeViewModel.currentHole == 1) {
            Button(
                onClick = {

                },
            ) {
                Text(
                    text = "-"
                )
            }
        } else {
            Button(
                onClick = {
                    holeViewModel.currentHole--
                },
            ) {
                Text(
                    text = "${holeViewModel.currentHole - 1}"
                )
            }
        }

        Box(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = holeViewModel.currentHole.toString(),
                modifier = Modifier.padding(16.dp)
            )
        }
        if (holeViewModel.currentHole == 18) {
            Button(
                onClick = {

                },
            ) {
                Text(
                    text = "-"
                )
            }
        } else {
            Button(
                onClick = {
                    holeViewModel.currentHole++
                },
            ) {
                Text(
                    text = "${holeViewModel.currentHole + 1}"
                )
            }
        }

    }
}