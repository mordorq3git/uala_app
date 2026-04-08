package com.example.ualaapp.repository.implementations.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id_favorite: Int = 0,
    val id_user: Long,
    val _id: Int
)