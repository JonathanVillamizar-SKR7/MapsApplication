package com.example.mapsapplication.presentation.screen.map

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapsapplication.presentation.permissions.AppPermission
import com.example.mapsapplication.presentation.permissions.PermissionContent
import com.example.mapsapplication.presentation.permissions.PermissionStatus
import com.example.mapsapplication.presentation.permissions.rememberPermissionManager
import com.example.mapsapplication.presentation.screen.viewmodel.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel,
    navigateToAddMarker: (Double, Double) -> Unit
) {
    val permissionManager = rememberPermissionManager(AppPermission.Location)
    val uiState by viewModel.uiState

    LaunchedEffect(permissionManager.status) {
        if (permissionManager.status == PermissionStatus.Unknown) {
            permissionManager.requestPermissions()
        }

        viewModel.onPermissionResult(permissionManager.status)
    }

    when (uiState) {
        MapPermissionState.NavigateToMap -> {
            val initialLocation = viewModel.initialLocation
            val markers = viewModel.markers

            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(initialLocation, 17f)
            }

            val mapProperties = MapProperties(
                isMyLocationEnabled = true
            )

            val mapUiSettings = MapUiSettings(
                zoomControlsEnabled = true, myLocationButtonEnabled = true, compassEnabled = true
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                    uiSettings = mapUiSettings,
                    onMapClick = { latLng ->
                        Log.d("MAP_CLICKED", latLng.toString())
                    },
                    onMapLongClick = { latLng ->
                        Log.d("MAP_CLICKED_LONG", latLng.toString())
                        navigateToAddMarker(latLng.latitude, latLng.longitude)
                    }) {
                    // Pintamos todos los marcadores guardados en el estado compartido
                    markers.forEach { marker ->
                        Marker(
                            state = rememberMarkerState(
                                position = LatLng(
                                    marker.latitude, marker.longitude
                                )
                            ), title = marker.title, snippet = marker.description
                        )
                    }
                }

                // Pequeña ayuda visual para recordar cómo crear un marcador
                Surface(
                    modifier = Modifier.padding(16.dp),
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    tonalElevation = 4.dp,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = "Long press on the map to add a new marker",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        MapPermissionState.ShowDenied -> {
            PermissionContent(
                status = PermissionStatus.Denied, onRetry = permissionManager.requestPermissions
            )
        }

        MapPermissionState.ShowPermanentlyDenied -> {
            PermissionContent(
                status = PermissionStatus.PermanentlyDenied, onRetry = {})
        }

        MapPermissionState.Requesting -> {
            PermissionContent(
                status = PermissionStatus.Unknown, onRetry = permissionManager.requestPermissions
            )
        }
    }
}