package com.example.mapsapplication.domain.model

data class MapMarker(
    val id: Int,
    val remoteId: String? = null,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUri: String? = null
)