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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    val initLocation = LatLng(-34.6037, -58.3816) // Buenos Aires

    val displayPosition = currentCity?.let {
        LatLng(it.coord.lat, it.coord.lon)
    } ?: initLocation

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(displayPosition, 10f)
    }
    val markerState = rememberMarkerState(position = displayPosition)

    LaunchedEffect(selectedCityId) {
        if(selectedCityId != -1) {
            viewModel.onEvent(MapIntent.GetCity(selectedCityId))
        }
    }

    LaunchedEffect(currentCity) {
        if(selectedCityId != -1) {
            currentCity?.let { city ->
                if(city._id == selectedCityId) {
                    val cityPos = LatLng(city.coord.lat, city.coord.lon)
                    markerState.position = cityPos
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(cityPos, 10f),
                        durationMs = 1000
                    )
                }
            }
        }
    }
    /*LaunchedEffect(selectedCityId) {
        if(selectedCityId != -1) {
            currentCity?.let { city ->
                val cityPos = LatLng(city.coord.lat, city.coord.lon)
                markerState.position = cityPos
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(cityPos, 10f),
                    durationMs = 1000
                )
            }
        }
    }*/

    /*LaunchedEffect(currentCity?.coord) {
        currentCity?.let {
            markerState.position = LatLng(it.coord.lat, it.coord.lon)
        }
    }*/
    /*LaunchedEffect(displayPosition) {
        markerState.position = displayPosition

        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
                displayPosition,
                10f
            ),
            durationMs = 1000
        )
    }*/

    MapComponent(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        markerState = markerState,
        city = currentCity,
        onAddFavouriteEvent = { _id ->
            viewModel.onEvent(MapIntent.AddToFavourites(_id))
        },
        onRemoveFavouriteEvent = { _id ->
            viewModel.onEvent(MapIntent.RemoveFromFavourites(_id))
        }
    )
}

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    markerState: MarkerState,
    city: City?,
    onAddFavouriteEvent: (Int) -> Unit = {},
    onRemoveFavouriteEvent: (Int) -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()
    ) {
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = markerState,
                title = city?.name ?: "Ubicacion",
                onClick = {
                    true
                }
            )
        }

        city?.let { city ->
            val favouriteEvent = if(!city.isFavourite) onAddFavouriteEvent else onRemoveFavouriteEvent

            CityMapDetailCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(36.dp),
                name = city.name,
                country = city.country,
                lat = city.coord.lat,
                lon = city.coord.lon,
                isFavorite = city.isFavourite,
                onFavouriteClick = { favouriteEvent(city._id) }
            )
        }
    }
}

@Composable
fun CityMapDetailCard(
    modifier: Modifier = Modifier,
    name: String = "",
    country: String = "",
    lat: Double = 0.0,
    lon: Double = 0.0,
    isFavorite: Boolean = false,
    onFavouriteClick: () -> Unit = {}
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
                    text = name,
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

            IconButton(onClick = {
                //onFavouriteClick()
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
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
    CityMapDetailCard(
        name = "Gualeguaychú",
        country = "AR",
        lat = 1.0,
        lon = 2.0,
        isFavorite = true,
        onFavouriteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CityMapDetailCard_WithoutFavorite_Preview() {
    CityMapDetailCard(
        name = "Gualeguaychú",
        country = "AR",
        lat = 1.0,
        lon = 2.0,
        isFavorite = false,
        onFavouriteClick = {}
    )
}