package com.example.mapsapplication.data.repository

import com.example.mapsapplication.data.mapper.toDto
import com.example.mapsapplication.data.remote.model.SupabaseMarkerDto
import com.example.mapsapplication.domain.model.MapMarker
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class MarkerRepository(
    private val supabase: SupabaseClient
) {

    suspend fun getMarkers(): List<SupabaseMarkerDto> {
        return supabase.from("markers").select().decodeList<SupabaseMarkerDto>()
    }

    suspend fun addMarker(marker: MapMarker) {
        supabase.from("markers").insert(marker.toDto())
    }

    suspend fun updateMarker(remoteId: String, marker: MapMarker) {
        supabase.from("markers").update(marker.toDto()) {
            filter {
                eq("id", remoteId)
            }
        }
    }

    suspend fun deleteMarker(remoteId: String) {
        supabase.from("markers").delete {
            filter {
                eq("id", remoteId)
            }
        }
    }
}