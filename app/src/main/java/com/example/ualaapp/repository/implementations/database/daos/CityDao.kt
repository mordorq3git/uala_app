package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite

@Dao
interface CityDao {

    @Insert
    suspend fun insert(cityEntity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listOfCityEntities: List<CityEntity>)

    @Query("DELETE FROM cities")
    suspend fun cleanTable()

    @Query("SELECT * FROM cities ORDER BY name ASC")
    suspend fun getAll(): List<CityEntity>

    @Query("""
        SELECT cities.*, 
               (favourites._id IS NOT NULL) AS isFavorite 
        FROM cities 
        LEFT JOIN favourites ON cities._id = favourites._id 
        AND favourites.id_user = :userId ORDER BY cities.name ASC
    """)
    suspend fun getAllCitiesWithFavorite(userId: Long): List<CityWithFavorite>


    @Query("SELECT * FROM cities WHERE _id = :id")
    suspend fun get(id: Int): CityEntity

    @Transaction
    suspend fun refreshData(listOfCityEntities: List<CityEntity>) {
        cleanTable()
        insertAll(listOfCityEntities)
    }
}