package com.jack.gymtracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jack.gymtracker.ui.theme.GymTrackerTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.Card
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController


val Roboto = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

val Typography = Typography(
    defaultFontFamily = Roboto,
    body1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

// Landing Page Function. Handles the page that will display when the app opens.

@Composable
fun LandingPage(onOptionSelected: (String) -> Unit) {
    Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(
                text = "Choose an option:",
                style = MaterialTheme.typography.h4.copy(color = Color.White),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OptionCard("Set Tracker", onOptionSelected)
            OptionCard("Notes", onOptionSelected)
        }
    }
}

// Handles the Cards that display the different features inside the application (Set Tracker, Notes)


@Composable
fun OptionCard(option: String, onOptionSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onOptionSelected(option) },
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text (
                text = option,
                // Styling for the text
                style = MaterialTheme.typography.h5.copy(color = Color.Black)
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // navController for landing page
            val navController = rememberNavController()
            MaterialTheme(
                // Typography for font
                typography = Typography,
                // Sets main content of MainActivity to be the UI
                content = {
                    Surface(modifier = Modifier.fillMaxSize(), color = Color.DarkGray) {
                        NavHost(navController, startDestination = "landingPage") {
                            composable("landingPage") {
                                LandingPage{ option ->
                                    when (option) {
                                        // When Set Tracker card is tapped, navigate to that page
                                        "Set Tracker" -> navController.navigate("setTracker")
                                        // When Notes card is tapped, navigate to that page
                                        "Notes" -> navController.navigate("notes")
                                    }
                                }

                            }
                            composable ("setTracker") {
                                GymTrackerUI(navController)
                            }
                            composable ("notes") {
                                notes(navController)
                            }
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun notes(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    "Notes",
                    color = Color.White,
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = Color.DarkGray,
            contentColor = Color.White
        )
    }
}
/* This is the function that handles the set tracker */
/* It uses the androidx.compose class to handle the UI through functions */

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GymTrackerUI(navController: NavController) {
    val totalSets = remember { mutableStateOf(5) }
    var currentSet by remember { mutableStateOf(0) }
    var timerValue by remember { mutableStateOf(0) }
    var timerRunning by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Set Tracker", color = Color.White, style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = Color.DarkGray,
            contentColor = Color.White
        )

// Set Count Button
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))
            ) {
                Text(text = "Set Count", style = MaterialTheme.typography.h5)
            }

            // Current set and Remaining sets text
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Current Set: $currentSet",
                style = MaterialTheme.typography.h5.copy(color = Color.White),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Remaining Sets: ${totalSets.value - currentSet}",
                style = MaterialTheme.typography.h5.copy(color = Color.White)
            )
        }


        // Timer
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { timerRunning = !timerRunning },
                    contentPadding = PaddingValues(horizontal = 30.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF03A9F4))
                ) {
                    Text(
                        text = if (timerRunning) "Stop Timer" else "Start Timer",
                        style = MaterialTheme.typography.h5
                    )
                }

                Text(
                    text = "Timer: $timerValue",
                    style = MaterialTheme.typography.h4.copy(color = Color.White),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            LaunchedEffect(timerRunning) {
                if (timerRunning) {
                    while (timerRunning) {
                        delay(1000L)
                        timerValue++
                    }
                } else {
                    timerValue = 0
                }
            }
        }

        // Next Set and Reset Buttons
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { if (currentSet < totalSets.value) currentSet++ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Next Set", style = MaterialTheme.typography.h5)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Adds space between buttons

            // Reset button
            Button(
                onClick = { currentSet = 0 }, // Resets currentSet to 0
                modifier = Modifier
                    .fillMaxWidth() // Button spans width of screen
                    .padding(horizontal = 16.dp) // Adds padding
                    .clip(RoundedCornerShape(12.dp)),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)) // Light blue
            ) {
                Text(text = "Reset", style = MaterialTheme.typography.h5)
            }
        }

        // Checks to see if showDialog is true
        if (showDialog) {
            // Composable function to display alert dialog on screen
            AlertDialog(
                // Lambda function called when user tries to exit alertDialog, basically closes dialog
                onDismissRequest = { showDialog = false },
                // Title of the dialog alert
                title = { Text(text = "Set Count") },
                // Composable function to display content in the dialog alert.
                text = {
                    TextField(
                        // Sets initial value
                        value = totalSets.value.toString(),
                        // This handles how the value changes when the user enters a number
                        onValueChange = { newValue ->
                            totalSets.value = if (newValue.isEmpty()) 0 else newValue.toIntOrNull() ?: totalSets.value
                        },
                        // User can only enter a number
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Confirm")
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
/* The greeting preview is so we can see the work as we are coding */

fun GreetingPreview() {
    GymTrackerTheme {
        // Create a fake navController for preview purposes
        val navController = rememberNavController()
        GymTrackerUI(navController)
    }
}

