package com.example.ualaapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ualaapp.presentation.loading.LoadingViewModel

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    viewModel: LoadingViewModel = hiltViewModel()
) {
    val loadingText = "Cargando ciudades..."

    LoadingComponent(modifier, loadingText)
}

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier,
    loadingText: String
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Text(
                text = loadingText,
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingScreen_Preview() {
    LoadingComponent(loadingText = "Cargando ciudades")
}