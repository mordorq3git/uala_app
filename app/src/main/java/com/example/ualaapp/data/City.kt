package com.example.ualaapp.data

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val _id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates,
    val isFavourite: Boolean = false
)