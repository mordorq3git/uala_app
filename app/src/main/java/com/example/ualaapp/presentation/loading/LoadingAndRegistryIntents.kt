package com.example.ualaapp.presentation.loading

sealed interface LoadingIntent {
    object Load : LoadingIntent
}

sealed interface RegistryIntent {
    data class SetUserName(val username: String) : RegistryIntent
    object Register : RegistryIntent
}