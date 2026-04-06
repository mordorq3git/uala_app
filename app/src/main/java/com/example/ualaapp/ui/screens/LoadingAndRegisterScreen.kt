package com.example.ualaapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ualaapp.R
import com.example.ualaapp.presentation.loading.LoadingAndRegistryUIState
import com.example.ualaapp.presentation.loading.LoadingAndRegisterViewModel
import com.example.ualaapp.presentation.loading.LoadingIntent
import com.example.ualaapp.presentation.loading.RegistryIntent
import com.example.ualaapp.ui.theme.UalaAppTheme

@Composable
fun LoadingAndRegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: LoadingAndRegisterViewModel = hiltViewModel()
) {
    val loadingState by viewModel.loadingAndRegistryUIState.collectAsStateWithLifecycle()
    val registerUserValue by viewModel.registerUserValue.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(LoadingIntent.Load)
    }

    when(loadingState) {
        LoadingAndRegistryUIState.Idle -> {}
        LoadingAndRegistryUIState.Loading -> { LoadingComponent(modifier) }
        LoadingAndRegistryUIState.Success -> {
            RegisterComponent(modifier, userName = registerUserValue, onValueChangeEvent = { name ->
                viewModel.onEvent(RegistryIntent.SetUserName(name))
            })
        }
    }
}

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier
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
                text = stringResource(R.string.loading_cities),
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun RegisterComponent(
    modifier: Modifier = Modifier,
    userName: String = "",
    onValueChangeEvent: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome),
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 20.sp
            )
            TextField(
                value = userName,
                onValueChange = { newValue ->
                    onValueChangeEvent(newValue)
                },
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .testTag("register_with_username_textfield"),
                placeholder = {
                    Text(text = stringResource(R.string.user_name))
                }
            )
            Button(
                {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.enter))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingScreen_Preview() {
    LoadingComponent()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingScreen_DarkTheme_Preview() {
    UalaAppTheme(darkTheme = true) {
        LoadingComponent()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreen_Preview() {
    RegisterComponent()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenDarkTheme_Preview() {
    UalaAppTheme(darkTheme = true) {
        RegisterComponent()
    }
}