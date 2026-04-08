package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite
import com.example.ualaapp.repository.implementations.database.entities.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert
    suspend fun insert(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE id_user = :id_user AND _id = :city_id")
    suspend fun delete(id_user: Long, city_id: Int)

    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE id_user = :id_user AND _id = :city_id)")
    fun existFavourite(id_user: Long, city_id: Int) : Flow<Boolean>

    @Query("""
        SELECT cities.*, 
           (f._id IS NOT NULL) AS isFavorite 
        FROM cities 
            LEFT JOIN favourites AS f ON cities._id = f._id 
            AND f.id_user = :userId
        WHERE cities._id = :id
            LIMIT 1
    """)
    suspend fun getCityFavorited(userId: Long, id: Int) : CityWithFavorite
}