package com.example.ualaapp.repository.implementations.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val id_user: Int,
    val _id: String
)