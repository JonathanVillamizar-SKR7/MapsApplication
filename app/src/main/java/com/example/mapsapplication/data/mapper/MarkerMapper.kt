package com.example.mapsapplication.data.mapper

import com.example.mapsapplication.data.remote.model.SupabaseMarkerDto
import com.example.mapsapplication.domain.model.MapMarker

fun SupabaseMarkerDto.toDomain(): MapMarker {
    return MapMarker(
        id = id?.hashCode() ?: 0,
        remoteId = id,
        title = title,
        description = description.orEmpty(),
        latitude = latitude,
        longitude = longitude,
        imageUri = image_url
    )
}

fun MapMarker.toDto(): SupabaseMarkerDto {
    return SupabaseMarkerDto(
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        image_url = imageUri
    )
}