package com.example.mapsapplication.presentation.screen.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Guarda la última imagen capturada para poder mostrarla en pantalla
class CameraViewModel : ViewModel() {

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri = _imageUri

    // Actualiza la uri de la imagen actual
    fun setImage(uri: Uri?) {
        _imageUri.value = uri
    }
}