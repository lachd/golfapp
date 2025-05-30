package com.example.golfapp.ui.mainmenu

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.golfapp.ui.theme.darkGreen
import com.example.golfapp.ui.theme.lightGreen

@Composable
fun MainMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                listOf<Color>(
                    Color.Transparent,
                    MaterialTheme.colorScheme.background
                ))
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            val gradientColors = listOf(Color.Black, Color.DarkGray, lightGreen, darkGreen)
            Text(
                text = "Unnamed Golf App",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        MainMenuItem(
            "New Round",
            onClick = { navController.navigate("new_round") },
            modifier = Modifier.weight(1f)
        )
        MainMenuItem(
            text = "History",
            onClick = { navController.navigate("history") },
            Modifier.weight(1f)
        )
        MainMenuItem(
            "Stats",
            onClick = { navController.navigate("stats") },
            Modifier.weight(1f)
        )
        MainMenuItem(
            "Settings",
            onClick = { navController.navigate("settings") },
            Modifier.weight(1f)
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MainMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                println("clicked ${text} box!")
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}
