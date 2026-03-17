package com.example.mapsapplication.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseMarkerDto(
    val id: String? = null,
    val title: String,
    val description: String? = null,
    val latitude: Double,
    val longitude: Double,
    val image_url: String? = null
)