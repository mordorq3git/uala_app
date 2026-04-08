package com.example.ualaapp.repository.implementations.database

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.Repository
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository : Repository {
    suspend fun saveCities(listOfCities: List<City>)

    fun getCitiesFiltered(userId: Long, query: String) : Flow<List<City>>

    suspend fun getCities(): List<City>

    fun getCities(userId: Long) : Flow<List<City>>

    fun getCity(userId: Long, id: Int): Flow<City>

    suspend fun saveUser(username: String) : Long

    suspend fun getUser(id: Long) : User

    suspend fun saveFavourite(userId: Long, cityId: Int)

    suspend fun removeFavourite(userId: Long, cityId: Int)
}