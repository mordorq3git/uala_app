package com.example.ualaapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val positionOfMarker = LatLng(-33.00938, -58.51722)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(positionOfMarker, 10f)
    }

    val marker = rememberMarkerState(position = positionOfMarker)

    MapComponent(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        markerState = marker
    )
}

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    markerState: MarkerState
) {
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = markerState,
            title = "Gualeguaychu",
            snippet = "Capital of Argentina"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen()
}