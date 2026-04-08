package com.example.ualaapp.presentation.map

sealed interface MapIntent {
    data class GetCity(val id: Int) : MapIntent
    data class AddToFavorites(val _id: Int) : MapIntent
    data class RemoveFromFavorites(val _id: Int) : MapIntent
    data class ShowMapCard(val shouldShow: Boolean) : MapIntent
}