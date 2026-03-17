package com.example.mapsapplication.presentation.screen.markerdetail

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapplication.presentation.screen.viewmodel.MapViewModel
import java.io.File

@Composable
fun MarkerDetailScreen(
    markerId: Int, navigateBack: () -> Unit, viewModel: MapViewModel
) {
    val context = LocalContext.current
    val marker = viewModel.getMarkerById(markerId) ?: return

    var title by remember(marker.id) { mutableStateOf(marker.title) }
    var description by remember(marker.id) { mutableStateOf(marker.description) }
    var selectedImageUri by remember(marker.id) {
        mutableStateOf(marker.imageUri?.let { Uri.parse(it) })
    }
    var showImageSourceOptions by remember { mutableStateOf(false) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                selectedImageUri = tempUri
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                tempUri?.let { uri ->
                    cameraLauncher.launch(uri)
                }
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Edit marker",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Update the marker information and change its image if needed.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Campos editables del marcador
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Mostramos la imagen actual si el marcador ya tiene una
                selectedImageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Marker image",
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                OutlinedButton(
                    onClick = {
                        showImageSourceOptions = true
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select image")
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (showImageSourceOptions) {
                    Button(
                        onClick = {
                            showImageSourceOptions = false
                            galleryLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Open gallery")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            showImageSourceOptions = false

                            val file = File.createTempFile(
                                "camera_image", ".jpg", context.cacheDir
                            )

                            tempUri = FileProvider.getUriForFile(
                                context, "${context.packageName}.provider", file
                            )

                            val cameraPermission = ContextCompat.checkSelfPermission(
                                context, Manifest.permission.CAMERA
                            )

                            if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                                tempUri?.let { uri ->
                                    cameraLauncher.launch(uri)
                                }
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Open camera")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Guardamos los cambios del marcador y volvemos a la pantalla anterior
                Button(
                    onClick = {
                        viewModel.updateMarker(
                            id = markerId,
                            title = title,
                            description = description,
                            imageUri = selectedImageUri?.toString()
                        )
                        navigateBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank() && description.isNotBlank()
                ) {
                    Text("Update marker")
                }
            }
        }
    }
}