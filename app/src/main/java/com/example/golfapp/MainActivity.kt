package com.example.golfapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.golfapp.database.GolfDatabase
import com.example.golfapp.ui.hole.HoleScreen
import com.example.golfapp.ui.mainmenu.MainMenuScreen
import com.example.golfapp.ui.newround.NewRoundScreen
import com.example.golfapp.ui.roundstats.RoundStatsScreen
import com.example.golfapp.ui.theme.GolfAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.round

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GolfAppTheme (
                dynamicColor = false
            ){
                Scaffold (
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main_menu",
                    ) {
                        composable("main_menu") {
                            MainMenuScreen(navController)
                        }
                        composable("new_round") {
                            NewRoundScreen(navController)
                        }
                        composable("history") {
                            HistoryScreen(navController)
                        }
                        composable("stats") {
                            StatsScreen(navController)
                        }
                        composable("settings") {
                            SettingsScreen(navController)
                        }
                        composable(
                            "hole/{roundId}",
                            arguments = listOf(
                                navArgument("roundId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val roundId = backStackEntry.arguments?.getInt("roundId") ?: -1
                            HoleScreen(
                                roundId = roundId,
                                navController = navController
                            )
                        }
                        composable(
                            "round_stats/{roundId}",
                            arguments = listOf(
                                navArgument("roundId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val roundId = backStackEntry.arguments?.getInt("roundId") ?: -1
                            RoundStatsScreen(
                                roundId = roundId,
                                navController = navController
                            )
                        }
                    }

                    innerPadding
                }
            }
        }
    }
}


@Composable
fun HistoryScreen(navController: NavHostController) {
    Column {
        Spacer(modifier = Modifier.padding(16.dp))
        for(i in 1..10){
            Text(text = "here's your results for round $i")
        }
        Button(onClick = {
            navController.navigate("main_menu")
        }) {
            Text(text = "go home")
        }
    }
}



@Composable
fun StatsScreen(navController: NavController) {
    Scaffold () { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "here are your stats!"
            )
            Button(onClick = {
                navController.navigate("main_menu")
            }) {
                Text(text = "go home")
            }
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold () { innerPadding ->
            Column (
                modifier = Modifier.padding(innerPadding)
            ) {
            Text(
                text = "do some settings stuff I guess"
            )
            Button(onClick = {
                navController.navigate("main_menu")
            }) {
                Text(text = "go home")
            }
        }
    }
}
