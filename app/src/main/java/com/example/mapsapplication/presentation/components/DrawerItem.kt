package com.example.mapsapplication.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mapsapplication.presentation.navigation.Destination

/**
 * Representa los elementos que aparecen en el Drawer de navegación.
 * Cada item tiene:
 * - un icono
 * - el texto que se muestra en el menú
 * - el destino de navegación asociado
 */
enum class DrawerItem(
    val icon: ImageVector, val text: String, val destination: Destination
) {

    // Pantalla principal con el mapa y los marcadores
    MAPS(
        icon = Icons.Default.Map, text = "Map", destination = Destination.Home
    ),

    // Lista de todos los marcadores creados por el usuario
    MARKER_LIST(
        icon = Icons.Default.List, text = "Marker List", destination = Destination.MarkerListScreen
    )

}