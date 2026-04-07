package com.example.ualaapp.repository

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User

interface BaseRepository : Repository {
    suspend fun loadCities()

    suspend fun getCities(): List<City>

    suspend fun getCity(id: Int) : City

    suspend fun saveUser(username: String)

    suspend fun getUser(username: String) : User

    suspend fun saveFavourite(cityId: Int)

    suspend fun removeFavourite(cityId: Int)
}