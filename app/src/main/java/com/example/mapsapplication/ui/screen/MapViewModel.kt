package com.example.mapsapplication.ui.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mapsapplication.ui.permissions.PermissionStatus
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {

    private val _uiState = mutableStateOf<MapPermissionState>(MapPermissionState.Requesting)
    val uiState: State<MapPermissionState> = _uiState

    private val _initialLocation = LatLng(41.4534225, 2.1837151)
    val initialLocation: LatLng = _initialLocation

    private val _markers = mutableStateListOf(_initialLocation)
    val markers: List<LatLng> get() = _markers

    fun onPermissionResult(status: PermissionStatus) {
        _uiState.value = when (status) {
            PermissionStatus.Granted -> MapPermissionState.NavigateToMap
            PermissionStatus.Denied -> MapPermissionState.ShowDenied
            PermissionStatus.PermanentlyDenied -> MapPermissionState.ShowPermanentlyDenied
            PermissionStatus.Unknown -> MapPermissionState.Requesting
        }
    }

    fun addMarker(latLng: LatLng) {
        _markers.add(latLng)
    }

    fun clearMarkers() {
        _markers.clear()
        _markers.add(_initialLocation)
    }
}