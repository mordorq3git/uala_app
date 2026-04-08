package com.example.ualaapp.repository.implementations.database.entities

import androidx.room.Embedded

data class CityWithFavorite(
    @Embedded val city: CityEntity,
    val isFavorite: Boolean
)