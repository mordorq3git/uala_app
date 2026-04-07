package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ualaapp.repository.implementations.database.entities.FavouriteEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity

@Dao
interface FavouriteDao {

    @Insert
    suspend fun insert(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE id_user = :id_user AND _id = :city_id")
    suspend fun delete(id_user: Long, city_id: Int)
}