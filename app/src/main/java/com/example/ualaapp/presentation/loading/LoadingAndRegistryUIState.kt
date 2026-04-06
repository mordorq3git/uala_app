package com.example.ualaapp.presentation.loading

sealed interface LoadingAndRegistryUIState {
    object Idle: LoadingAndRegistryUIState
    object Loading: LoadingAndRegistryUIState
    object Success : LoadingAndRegistryUIState
}