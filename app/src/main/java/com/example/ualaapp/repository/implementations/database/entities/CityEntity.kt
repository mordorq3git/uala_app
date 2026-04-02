package com.example.ualaapp.repository.implementations.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id_city: Int,
    val _id: Int,
    val name: String,
    val country: String,
    @Embedded val coord: CoordinatesEntity
)

data class CoordinatesEntity(
    val lon: Double,
    val lat: Double
)