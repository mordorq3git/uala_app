package com.example.ualaapp.repository.implementations.api

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository

interface ApiRepository : Repository {
    suspend fun loadCities(): List<City>
}