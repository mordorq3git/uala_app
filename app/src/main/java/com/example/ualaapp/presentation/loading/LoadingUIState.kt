package com.example.ualaapp.presentation.loading

import com.example.ualaapp.data.City

sealed interface LoadingUIState {
    object Idle: LoadingUIState
    object Loading: LoadingUIState
    object Success : LoadingUIState
}

sealed interface LoadingRegisterButtonUIState {
    object OnText : LoadingRegisterButtonUIState
}