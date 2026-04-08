package com.example.ualaapp.repository

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import kotlinx.coroutines.flow.Flow

interface BaseRepository : Repository {

    // Cities
    suspend fun loadCities()

    // Users
    fun getUserSessionId(): Long

    suspend fun saveUser(username: String)

    // Favourites
    suspend fun saveFavourite(cityId: Int)

    fun getCitiesWithFavouritesFlow(query: String): Flow<List<City>>

    suspend fun getCityFavorited(cityId: Int): City

    suspend fun removeFavourite(cityId: Int)

    fun existFavourite(userId: Long, cityId: Int): Flow<Boolean>
}