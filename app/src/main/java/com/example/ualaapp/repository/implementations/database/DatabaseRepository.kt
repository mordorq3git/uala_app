package com.example.ualaapp.repository.implementations.database

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite

interface DatabaseRepository : Repository {
    suspend fun saveCities(listOfCities: List<City>)

    suspend fun getCities(): List<City>

    suspend fun getCities(userId: Long) : List<City>

    suspend fun getCity(id: Int): City

    suspend fun saveUser(username: String) : Long

    suspend fun getUser(id: Long) : User

    suspend fun saveFavourite(userId: Long, cityId: Int)

    suspend fun removeFavourite(userId: Long, cityId: Int)
}