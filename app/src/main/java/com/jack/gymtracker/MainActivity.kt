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




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    GymTrackerUI()
                }
            }
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
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Exercise Name", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Current Set: $currentSet", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Remaining Sets: ${totalSets - currentSet}", style = MaterialTheme.typography.h5)
        }

        Button(
            onClick = { if (currentSet < totalSets) currentSet++ },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 12.dp)
        ) {
            Text(text = "Next Set", style = MaterialTheme.typography.h5)
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

