package com.example.ualaapp.presentation.map

sealed interface MapIntent {
    data class GetCity(val id: Int) : MapIntent
    data class AddToFavourites(val _id: Int) : MapIntent
    data class RemoveFromFavourites(val _id: Int) : MapIntent
}