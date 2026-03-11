package com.example.mapsapplication.ui.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Esta clase guardará la última foto tomada
class CameraViewModel : ViewModel() {

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri = _imageUri

    fun setImage(uri: Uri?) {
        _imageUri.value = uri
    }
}