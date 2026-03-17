package com.example.mapsapplication.presentation.screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapsapplication.data.remote.supabase.SupabaseProvider
import com.example.mapsapplication.data.repository.MarkerRepository
import com.example.mapsapplication.domain.model.MapMarker
import com.example.mapsapplication.presentation.permissions.PermissionStatus
import com.example.mapsapplication.presentation.screen.map.MapPermissionState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel : ViewModel() {

    private val repository = MarkerRepository(SupabaseProvider.client)


    private val _uiState = mutableStateOf<MapPermissionState>(MapPermissionState.Requesting)
    val uiState: State<MapPermissionState> = _uiState

    // Ubicación inicial que se usa al cargar el mapa por primera vez
    private val _initialLocation = LatLng(41.4534225, 2.1837151)
    val initialLocation: LatLng = _initialLocation

    // Lista de marcadores que se muestra tanto en el mapa como en la lista
    private val _markers = mutableStateListOf<MapMarker>()

    val markers: List<MapMarker> get() = _markers

    init {
        loadMarkers()
    }

    // Actualiza el estado del mapa según el resultado del permiso de ubicación
    fun onPermissionResult(status: PermissionStatus) {
        _uiState.value = when (status) {
            PermissionStatus.Granted -> MapPermissionState.NavigateToMap
            PermissionStatus.Denied -> MapPermissionState.ShowDenied
            PermissionStatus.PermanentlyDenied -> MapPermissionState.ShowPermanentlyDenied
            PermissionStatus.Unknown -> MapPermissionState.Requesting
        }
    }

    // Añade un nuevo marcador a la lista actual
    fun addMarker(marker: MapMarker) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.addMarker(marker)
                }
                loadMarkers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Busca un marcador concreto a partir de su id
    fun getMarkerById(id: Int): MapMarker? {
        return _markers.find { it.id == id }
    }

    // Actualiza la información editable de un marcador existente
    fun updateMarker(
        id: Int,
        title: String,
        description: String,
        imageUri: String?
    ) {
        val marker = _markers.find { it.id == id } ?: return
        val remoteId = marker.remoteId ?: return

        val updatedMarker = marker.copy(
            title = title,
            description = description,
            imageUri = imageUri
        )

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.updateMarker(remoteId, updatedMarker)
                }
                loadMarkers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Elimina el marcador cuyo id coincide con el recibido
    fun deleteMarker(id: Int) {
        val marker = _markers.find { it.id == id } ?: return
        val remoteId = marker.remoteId ?: return

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deleteMarker(remoteId)
                }
                _markers.removeAll { it.id == id }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadMarkers() {
        viewModelScope.launch {
            try {
                val remoteMarkers = withContext(Dispatchers.IO) {
                    repository.getMarkers().map { dto ->
                        MapMarker(
                            id = dto.id?.hashCode() ?: 0,
                            remoteId = dto.id,
                                title = dto.title,
                            description = dto.description.orEmpty(),
                            latitude = dto.latitude,
                            longitude = dto.longitude,
                            imageUri = dto.image_url
                        )
                    }
                }

                _markers.clear()
                _markers.addAll(remoteMarkers)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}