package com.example.mapsapplication.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mapsapplication.ui.components.DrawerMenu
import com.example.mapsapplication.ui.navigation.Destination
import kotlinx.coroutines.launch
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavController, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            DrawerMenu(
                currentRoute = currentRoute,
                onNavigate = { destination ->
                    navController.navigate(destination) {
                        popUpTo (Destination.Home) { inclusive = false }
                        launchSingleTop = true
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }) {
        Scaffold(topBar = {
            TopAppBar(title = { Text("Mi app") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() }  }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
        ) { padding ->
            Box(Modifier.padding(padding)) { content() }
        }
    }
}