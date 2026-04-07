package com.example.ualaapp.repository.implementations.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id_favourite: Int = 0,
    val id_user: Long,
    val _id: Int
)