package com.example.mapsapplication.ui.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapplication.ui.permissions.AppPermission
import com.example.mapsapplication.ui.permissions.PermissionContent
import com.example.mapsapplication.ui.permissions.PermissionStatus
import com.example.mapsapplication.ui.permissions.rememberPermissionManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapsScreen(
    modifier: Modifier = Modifier, viewModel: MapViewModel = viewModel()
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

            GoogleMap(
                modifier = modifier,
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = mapUiSettings,
                onMapClick = { latLng ->
                    Log.d("MAP_CLICKED", latLng.toString())
                },
                onMapLongClick = { latLng ->
                    Log.d("MAP_CLICKED_LONG", latLng.toString())
                    viewModel.addMarker(latLng)
                }) {
                markers.forEachIndexed { index, latLng ->
                    Marker(
                        state = rememberMarkerState(position = latLng),
                        title = "Marker ${index + 1}",
                        snippet = "${latLng.latitude}, ${latLng.longitude}"
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