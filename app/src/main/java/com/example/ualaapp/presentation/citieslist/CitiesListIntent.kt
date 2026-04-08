package com.example.ualaapp.presentation.citieslist

sealed interface CitiesListIntent {
    data class Filter(val filter: String) : CitiesListIntent
    data class AddToFavorites(val _id: Int) : CitiesListIntent
    data class RemoveFromFavorites(val _id: Int) : CitiesListIntent
}