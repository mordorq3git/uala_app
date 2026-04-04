package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ualaapp.repository.implementations.database.entities.CityEntity

@Dao
interface CityDao {

    @Insert
    suspend fun insert(cityEntity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listOfCityEntities: List<CityEntity>)

    @Query("DELETE FROM cities")
    suspend fun cleanTable()

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAll(): List<CityEntity>

    @Transaction
    suspend fun refreshData(listOfCityEntities: List<CityEntity>) {
        cleanTable()
        insertAll(listOfCityEntities)
    }
}
