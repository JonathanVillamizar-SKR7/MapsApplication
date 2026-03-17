package com.example.mapsapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapplication.presentation.layout.MainScaffold
import com.example.mapsapplication.presentation.screen.addmarker.AddMarkerScreen
import com.example.mapsapplication.presentation.screen.viewmodel.MapViewModel
import com.example.mapsapplication.presentation.screen.map.MapsScreen
import com.example.mapsapplication.presentation.screen.markerdetail.MarkerDetailScreen
import com.example.mapsapplication.presentation.screen.markerlist.MarkerListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    // ViewModel compartido para que mapa, lista, crear y editar usen los mismos marcadores
    val mapViewModel: MapViewModel = viewModel()

    MainScaffold(navController = navController) {
        NavHost(
            navController = navController, startDestination = Destination.Home
        ) {

            // Pantalla principal con el mapa
            composable<Destination.Home> {
                MapsScreen(
                    viewModel = mapViewModel, navigateToAddMarker = { latitude, longitude ->
                        navController.navigate(
                            Destination.AddMarker(latitude, longitude)
                        )
                    })
            }

            // Pantalla con la lista de marcadores creados
            composable<Destination.MarkerListScreen> {
                MarkerListScreen(
                    viewModel = mapViewModel, navigateToDetail = { markerId ->
                        navController.navigate(
                            Destination.MarkerDetail(markerId)
                        )
                    })
            }

            // Pantalla de detalle y edición de un marcador
            composable<Destination.MarkerDetail> { backStackEntry ->
                val destination = backStackEntry.toRoute<Destination.MarkerDetail>()

                MarkerDetailScreen(
                    markerId = destination.markerId, viewModel = mapViewModel, navigateBack = {
                        navController.popBackStack()
                    })
            }

            // Pantalla para crear un marcador nuevo
            composable<Destination.AddMarker> { backStackEntry ->
                val destination = backStackEntry.toRoute<Destination.AddMarker>()

                AddMarkerScreen(
                    latitude = destination.latitude,
                    longitude = destination.longitude,
                    viewModel = mapViewModel,
                    navigateBack = {
                        navController.popBackStack()
                    })
            }
        }
    }
}