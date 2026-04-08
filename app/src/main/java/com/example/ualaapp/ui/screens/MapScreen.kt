package com.example.ualaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ualaapp.R
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.presentation.map.MapIntent
import com.example.ualaapp.presentation.map.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    selectedCityId: Int,
    viewModel: MapViewModel = hiltViewModel()
) {
    val currentCity by viewModel.currentCityState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.existFavourite.collectAsStateWithLifecycle()

    val initLocation = LatLng(-34.6037, -58.3816) // Buenos Aires

    val displayPosition: LatLng = currentCity?.let {
        LatLng(it.coord.lat, it.coord.lon)
    } ?: initLocation

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(displayPosition, 10f)
    }

    val markerState = currentCity?.let {
        rememberMarkerState(position = displayPosition)
    }

    LaunchedEffect(selectedCityId) {
        if(selectedCityId != -1) {
            viewModel.onEvent(MapIntent.GetCity(selectedCityId))
        }
    }

    LaunchedEffect(selectedCityId) {
        if(selectedCityId != -1) {
            viewModel.checkFavouriteStatus(selectedCityId)
        }
    }

    LaunchedEffect(currentCity) {
        currentCity?.let { city ->
            val cityPos = LatLng(city.coord.lat, city.coord.lon)
            markerState?.position = cityPos
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(cityPos, 10f),
                durationMs = 1000
            )
        }
    }

    MapComponent(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        markerState = markerState,
        isFavorite = isFavorite,
        city = currentCity,
        onAddFavoriteEvent = { _id ->
            viewModel.onEvent(MapIntent.AddToFavourites(_id))
        },
        onRemoveFavoriteEvent = { _id ->
            viewModel.onEvent(MapIntent.RemoveFromFavourites(_id))
        }
    )
}

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    markerState: MarkerState?,
    city: City?,
    isFavorite: Boolean = false,
    onAddFavoriteEvent: (Int) -> Unit = {},
    onRemoveFavoriteEvent: (Int) -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()
    ) {
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            markerState?.let {
                Marker(
                    state = it,
                    title = city?.name ?: "Ubicacion",
                    onClick = { true }
                )
            }
        }

        city?.let { city ->
            val favoriteEvent = if (!isFavorite) onAddFavoriteEvent else onRemoveFavoriteEvent

            MapCardComponent(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(36.dp),
                city = city.name,
                country = city.country,
                lat = city.coord.lat,
                lon = city.coord.lon,
                isFavorite = isFavorite,
                onFavoriteClick = { favoriteEvent(city._id) }
            )
        }
    }
}

@Composable
fun MapCardComponent(
    modifier: Modifier = Modifier,
    city: String = "",
    country: String = "",
    lat: Double = 0.0,
    lon: Double = 0.0,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = city,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = country,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Lat: ${lat}, Lon: ${lon}",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            IconButton(
                modifier = Modifier.testTag("favorite_icon_map_card"),
                onClick = {
                    onFavoriteClick()
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite)
                        stringResource(R.string.remove_from_favorites) else stringResource(R.string.add_to_favorites),
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapScreenPreview() {
    MapComponent(
        cameraPositionState = rememberCameraPositionState(),
        markerState = rememberMarkerState(position = LatLng(0.0, 0.0)),
        city = City(
            country = "AR",
            name = "Ciudad Autónoma de Buenos Aires",
            _id = 3433955,
            coord = Coordinates(
                lat = -58.450001,
                lon = -34.599998
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun CityMapDetailCard_WithFavorite_Preview() {
    MapCardComponent(
        city = "Gualeguaychú",
        country = "AR",
        lat = 1.0,
        lon = 2.0,
        isFavorite = true,
        onFavoriteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CityMapDetailCard_WithoutFavorite_Preview() {
    MapCardComponent(
        city = "Gualeguaychú",
        country = "AR",
        lat = 1.0,
        lon = 2.0,
        isFavorite = false,
        onFavoriteClick = {}
    )
}