package com.example.mapsapplication.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapsapplication.presentation.navigation.Destination

@Composable
fun DrawerMenu(
    currentRoute: String?, onNavigate: (Destination) -> Unit
) {

    // Drawer lateral que contiene las opciones principales de navegación
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // Título del menú
        Text(
            text = "GeoMark",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Navigation",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Recorremos los elementos definidos en DrawerItem
        DrawerItem.entries.forEach { item ->

            val selected = currentRoute == item.destination.toString()

            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.icon, contentDescription = item.text
                    )
                },
                label = {
                    Text(text = item.text)
                },
                selected = selected,
                onClick = {
                    onNavigate(item.destination)
                },
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                    .fillMaxWidth(),

                // Colores adaptados al theme
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}