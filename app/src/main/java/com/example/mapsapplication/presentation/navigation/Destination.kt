package com.example.mapsapplication.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination() {
    @Serializable
    object Home: Destination()
    @Serializable
    object MarkerListScreen: Destination()
    @Serializable
    data class MarkerDetail(val markerId: Int) : Destination()
    @Serializable
    data class AddMarker(
        val latitude: Double,
        val longitude: Double
    ) : Destination()
}
