package com.example.ualaapp.repository.implementations.database

import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.Repository

interface DatabaseRepository : Repository {
    suspend fun saveCities(listOfCities: List<City>)

    suspend fun getCities(): List<City>

    suspend fun getCity(id: Int): City

    suspend fun saveUser(username: String)

    suspend fun getUser(username: String) : User
}