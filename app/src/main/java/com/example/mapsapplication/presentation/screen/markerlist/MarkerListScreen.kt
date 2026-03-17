package com.example.mapsapplication.presentation.screen.markerlist

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapplication.domain.model.MapMarker
import com.example.mapsapplication.presentation.screen.viewmodel.MapViewModel

@Composable
fun MarkerListScreen(
    navigateToDetail: (Int) -> Unit, viewModel: MapViewModel
) {
    val markers = viewModel.markers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Marker list",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Here you can see all the markers created and open them to edit their information.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(markers, key = { it.id }) { marker ->

                val dismissState = rememberSwipeToDismissBoxState()

                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                    LaunchedEffect(marker.id) {
                        viewModel.deleteMarker(marker.id)
                    }
                }

                SwipeToDismissBox(
                    state = dismissState, backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete marker",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }) {
                    MarkerListItem(
                        marker = marker, onClick = { navigateToDetail(marker.id) })
                }
            }
        }
    }
}

@Composable
fun MarkerListItem(
    marker: MapMarker, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Si el marcador tiene imagen, la mostramos como preview
            marker.imageUri?.let { imageUri ->
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(imageUri)),
                    contentDescription = "Marker preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = marker.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = marker.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mostramos las coordenadas con un detalle visual más claro
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Coordinates",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "${marker.latitude}, ${marker.longitude}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}