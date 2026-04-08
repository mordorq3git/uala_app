package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite
import kotlinx.coroutines.flow.Flow

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
           (f._id IS NOT NULL) AS isFavorite 
        FROM cities 
            LEFT JOIN favourites AS f ON cities._id = f._id 
            AND f.id_user = :userId
        WHERE cities.name LIKE :query || '%'
            ORDER BY cities.name ASC 
            LIMIT 500
    """)
    fun getCitiesFilteredFlow(userId: Long, query: String): Flow<List<CityWithFavorite>>

    @Transaction
    suspend fun refreshData(listOfCityEntities: List<CityEntity>) {
        cleanTable()
        insertAll(listOfCityEntities)
    }
}