package com.example.ualaapp.repository.implementations.database

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.Repository
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository : Repository {
    suspend fun getCities(): List<City>

    suspend fun saveCities(listOfCities: List<City>)

    fun getCitiesFilteredFlow(userId: Long, query: String) : Flow<List<City>>

    suspend fun saveUser(username: String) : Long

    suspend fun getUser(id: Long) : User

    suspend fun saveFavourite(userId: Long, cityId: Int)

    suspend fun getCityFavorited(userId: Long, id: Int): City

    suspend fun removeFavourite(userId: Long, cityId: Int)

    fun existFavourite(userId: Long, cityId: Int) : Flow<Boolean>
}