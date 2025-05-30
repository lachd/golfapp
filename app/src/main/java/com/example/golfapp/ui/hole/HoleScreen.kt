package com.example.golfapp.ui.hole

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HoleScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize().
            padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "raggle",
            fontSize =  64.sp // MaterialTheme.typography.titleLarge.fontSize
        )
        Text(
            text = "fraggle",
            fontSize =  64.sp // MaterialTheme.typography.titleLarge.fontSize
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            navController.navigate("main_menu")
        }) {
            Text(
                text = "go home",
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        }
    }
}