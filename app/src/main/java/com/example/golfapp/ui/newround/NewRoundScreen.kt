package com.example.golfapp.ui.newround

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox

import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.golfapp.util.Weather
import com.example.golfapp.util.Wind

@Composable
fun NewRoundScreen(
    navController: NavHostController,
    newRoundViewModel: NewRoundViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        newRoundViewModel.checkForActiveRound { activeRoundId ->
            navController.navigate("hole/$activeRoundId") {
                popUpToId
                // TODO - maybe this should be:
                //  navController.navigate("hole/$activeRoundId") {
                //     popUpTo("newround") { inclusive = true }
                //  }
            }
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)) {
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
            Spacer(modifier = Modifier.padding(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Row(modifier = Modifier.padding(vertical = 0.dp)) {
                    Text(
                        text = "Start New Round",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                CourseDropDown(
                    modifier = Modifier.padding(bottom = 8.dp),
                    courseList = newRoundViewModel.courses,
                    selectedCourse = newRoundViewModel.selectedCourse,
                    onOptionSelected = {
                        newRoundViewModel::selectCourse
                    }
                )

                WeatherSelect(
                    selectedOption = newRoundViewModel.selectedWeather,
                    onOptionSelected = { weather ->
                        newRoundViewModel::selectWeather
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                WindSelect(
                    selectedOption = newRoundViewModel.selectedWind,
                    onOptionSelected = { wind ->
                        newRoundViewModel::selectWind
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                StartNewRound(
                    navController,
                    newRoundViewModel,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Composable
fun StartNewRound(
    navController: NavController,
    newRoundViewModel: NewRoundViewModel,
    modifier: Modifier = Modifier
) {
    Button (modifier = modifier
        .fillMaxWidth(),
        onClick = {
            newRoundViewModel.startNewRound { roundId ->
                navController.navigate("hole/$roundId") {
                    popUpToId
                }
            }
        }
    ) {
        Text(
            text = "Start Round!",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun WindSelect(
    selectedOption: Wind,
    onOptionSelected: (Wind) -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Air,
            contentDescription = "weather",
            modifier = modifier.padding(8.dp)
        )
        Wind.entries.forEach { option ->
            SelectableChip(
                text = option.toString(),
                isSelected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSelect(
    selectedOption: Weather,
    onOptionSelected: (Weather) -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.WbSunny,
            contentDescription = "weather",
            modifier = modifier.padding(8.dp)
        )
        Weather.entries.forEach { option ->
            SelectableChip(
                text = option.toString(),
                isSelected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SelectableChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier
            .clickable { onClick() },
        shape = RectangleShape,
        color = backgroundColor,
        tonalElevation = if (isSelected) 6.dp else 2.dp
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDropDown(
    modifier: Modifier = Modifier,
    courseList: List<String>,
    selectedCourse: String,
    onOptionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.GolfCourse,
                contentDescription = "",
                modifier = Modifier.padding(8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth(),
                    value = selectedCourse,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                ) {
                    courseList.forEachIndexed { index, string ->
                        DropdownMenuItem(
                            text = { Text(text = string) },
                            onClick = {
                                onOptionSelected(courseList[index])
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                        )
                    }
                }

            }
        }
    }
}