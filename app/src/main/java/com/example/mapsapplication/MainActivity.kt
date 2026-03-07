package com.example.mapsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mapsapplication.ui.navigation.AppNavHost
import com.example.mapsapplication.ui.theme.MapsApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapsApplicationTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}