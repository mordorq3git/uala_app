package com.example.ualaapp.presentation.loading

sealed interface LoadingIntent {
    object Load : LoadingIntent
}

sealed interface RegistryIntent {
    data class UpdateUsername(val username: String) : RegistryIntent
    object Register : RegistryIntent
}