package com.example.ualaapp.presentation.citieslist

sealed interface CitiesListIntent {
    object Get : CitiesListIntent
    data class Filter(val filter: String) : CitiesListIntent
    data class AddToFavourites(val _id: Int) : CitiesListIntent
    data class RemoveFromFavourites(val _id: Int) : CitiesListIntent
}