package com.example.nammakathey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nammakathey.ui.navigation.NavGraph
import com.example.nammakathey.ui.theme.NammaKatheyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // We use 'remember' here to keep the language state while the app is running
        // For a production app, you would use DataStore or SharedPreferences to save this permanently.
        setContent {
            var isEnglish by remember { mutableStateOf(true) }
            val navController = rememberNavController()

            NammaKatheyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        navController = navController,
                        isEnglish = isEnglish,
                        onLanguageToggle = { isEnglish = !isEnglish }
                    )
                }
            }
        }
    }
}