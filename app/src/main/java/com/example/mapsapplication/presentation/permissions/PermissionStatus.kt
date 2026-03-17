package com.example.mapsapplication.presentation.permissions

sealed class PermissionStatus {
    object Unknown : PermissionStatus()
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()
}