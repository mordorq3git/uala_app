package com.example.ualaapp.repository.implementations.database

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository

interface DatabaseRepository : Repository {
    suspend fun setCities(listOfCities: List<City>)

    suspend fun getCities(): List<City>
}