package com.example.ualaapp.repository

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import kotlinx.coroutines.flow.Flow

interface BaseRepository : Repository {
    suspend fun loadCities()

    suspend fun getCities(): List<City>

    fun getCitiesWithFavourites(query: String): Flow<List<City>>

    fun getCity(id: Int) : Flow<City>

    suspend fun getUniqueCity(id: Int) : City

    suspend fun saveUser(username: String)

    suspend fun getUser(username: String) : User

    suspend fun saveFavourite(cityId: Int)

    suspend fun removeFavourite(cityId: Int)
}