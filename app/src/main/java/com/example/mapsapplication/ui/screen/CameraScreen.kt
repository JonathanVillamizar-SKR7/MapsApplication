package com.example.mapsapplication.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun CameraScreen(
    viewModel: CameraViewModel = viewModel()
) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri

    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.setImage(tempUri)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                val file = File.createTempFile(
                    "camera_image", ".jpg", context.cacheDir
                )

                tempUri = FileProvider.getUriForFile(
                    context, "${context.packageName}.provider", file
                )
                tempUri?.let { uri ->
                    launcher.launch(uri)
                }
            }) {
            Text("Take photo")
        }

        Spacer(modifier = Modifier.height(20.dp))

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Captured image",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}