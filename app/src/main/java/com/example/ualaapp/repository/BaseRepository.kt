package com.example.ualaapp.repository

import com.example.ualaapp.data.City
import kotlinx.coroutines.flow.Flow

interface BaseRepository : Repository {

    // Cities
    suspend fun loadCities()

    // Users
    fun getUserSessionId(): Long

    suspend fun saveUser(username: String)

    // Favorites
    suspend fun saveFavorite(cityId: Int)

    fun getCitiesWithFavoritesFlow(query: String): Flow<List<City>>

    suspend fun getCityFavorited(cityId: Int): City

    suspend fun removeFavorite(cityId: Int)

    fun existFavorite(userId: Long, cityId: Int): Flow<Boolean>
}