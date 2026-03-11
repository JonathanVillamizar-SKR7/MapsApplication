package com.example.mapsapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.mapsapplication.ui.layout.MainScaffold
import com.example.mapsapplication.ui.screen.AboutScreen
import com.example.mapsapplication.ui.screen.CameraScreen
import com.example.mapsapplication.ui.screen.MapsScreen
import com.example.mapsapplication.ui.screen.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    MainScaffold(navController) {
        NavHost(navController = navController, startDestination = Destination.Home) {
            composable<Destination.Home> { MapsScreen() }
            composable<Destination.Settings> { SettingsScreen() }
            composable<Destination.About> { AboutScreen() }
            composable<Destination.Camera> {
                CameraScreen()
            }
        }
    }
}