package com.example.mapsapplication.ui.permissions

sealed class PermissionStatus {
    object Unknown : PermissionStatus()
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()
}