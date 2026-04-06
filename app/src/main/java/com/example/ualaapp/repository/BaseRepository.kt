package com.example.ualaapp.repository

import com.example.ualaapp.data.City

interface BaseRepository : Repository {
    suspend fun loadCities()

    suspend fun getCities(): List<City>

}