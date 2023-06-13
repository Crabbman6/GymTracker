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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                // Typography for font
                typography = Typography,
                // Sets main content of MainActivity to be the UI
                content = {
                    Surface(modifier = Modifier.fillMaxSize(), color = Color.DarkGray) {
                        GymTrackerUI()
                    }
                }
            )
        }
    }
}

/* This is the function that handles the UI for the main page */
/* It uses the androidx.compose class to handle the UI through functions */

@Composable
fun GymTrackerUI() {
    val totalSets = 5  // Change this to the total number of sets
    var currentSet by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Exercise Name",
                style = MaterialTheme.typography.h4.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Current Set: $currentSet",
                style = MaterialTheme.typography.h5.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Remaining Sets: ${totalSets - currentSet}",
                style = MaterialTheme.typography.h5.copy(color = Color.White)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            // Next set button
            Button(
                onClick = { if (currentSet < totalSets) currentSet++ },
                modifier = Modifier
                    .fillMaxWidth() // Button spans width of the screen
                    .padding(horizontal = 16.dp) // Adds padding
                    .clip(RoundedCornerShape(12.dp)), // Makes corners of the button rounded
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
    }
}


@Preview(showBackground = true)
@Composable
/* The greeting preview is so we can see the work as we are coding */

fun GreetingPreview() {
    GymTrackerTheme {
        GymTrackerUI()
    }
}

