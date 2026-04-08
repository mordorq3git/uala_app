package com.example.ualaapp.repository

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import kotlinx.coroutines.flow.Flow

interface BaseRepository : Repository {


    // Cities
    suspend fun loadCities()

    fun getCitiesWithFavouritesFlow(query: String): Flow<List<City>>

    fun getCityFavoritedFlow(id: Int) : Flow<City>

    suspend fun getCityFavorited(id: Int) : City


    // Users
    suspend fun saveUser(username: String)

    suspend fun getUser(username: String) : User


    // Favourites
    suspend fun saveFavourite(cityId: Int)

    suspend fun removeFavourite(cityId: Int)
}