package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity

@Dao
interface CityDao {

    @Insert
    suspend fun insert(cityEntity: CityEntity)

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAll(): List<City>

}