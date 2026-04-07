package com.example.ualaapp.presentation.citieslist

sealed interface CitiesListIntent {
    object Get : CitiesListIntent
    data class Filter(val filter: String) : CitiesListIntent
}