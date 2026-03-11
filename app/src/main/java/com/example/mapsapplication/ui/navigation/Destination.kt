package com.example.mapsapplication.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination() {
    @Serializable
    object Home: Destination()
    @Serializable
    object Settings: Destination()
    @Serializable
    object About: Destination()
    @Serializable
    object Camera: Destination()
}
