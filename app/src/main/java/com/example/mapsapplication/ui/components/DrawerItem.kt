package com.example.mapsapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mapsapplication.ui.navigation.Destination

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val destination: Destination
) {
    HOME(Icons.Default.Home, "Home", Destination.Home),
    SETTINGS(Icons.Default.Settings, "Settings", Destination.Settings),
    ABOUT(Icons.Default.Info, "About", Destination.About),
}