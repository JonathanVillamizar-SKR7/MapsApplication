package com.example.mapsapplication.presentation.permissions

import android.Manifest

// Agrupa los permisos que usa la app según la funcionalidad
sealed class AppPermission(
    val permissions: List<String>
) {

    // Permiso para acceder a la ubicación del usuario
    data object Location : AppPermission(
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    )

    // Permisos para funcionalidades de cámara y grabación de audio
    data object CameraAndAudio : AppPermission(
        listOf(
            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
        )
    )
}