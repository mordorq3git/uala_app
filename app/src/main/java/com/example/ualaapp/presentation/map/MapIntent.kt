package com.example.ualaapp.presentation.map

sealed interface MapIntent {
    data class GetCity(val id: Int) : MapIntent
}