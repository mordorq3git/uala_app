package com.example.ualaapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ualaapp.presentation.map.MapIntent
import com.example.ualaapp.presentation.map.MapViewModel
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

    LaunchedEffect(displayPosition) {
        markerState.position = displayPosition

        cameraPositionState.animate(
            update = com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                displayPosition,
                10f
            ),
            durationMs = 1000
        )
    }

    MapComponent(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        markerState = markerState,
        title = currentCity?.let { "${it.name}, ${it.country}" } ?: "Buenos Aires, AR",
        snippet = currentCity?.let { "${it.coord.lat}, ${it.coord.lon}" } ?: "Ubicación por defecto"
    )
}

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    markerState: MarkerState,
    title: String = "",
    snippet: String = ""
) {
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = markerState,
            title = title,
            snippet = snippet
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapComponent(
        cameraPositionState = rememberCameraPositionState(),
        markerState = rememberMarkerState(position = LatLng(0.0, 0.0)),
        title = "Buenos Aires, AR",
        snippet = ""
    )
}