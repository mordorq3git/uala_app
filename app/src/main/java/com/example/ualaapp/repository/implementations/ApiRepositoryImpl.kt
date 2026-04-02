package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository

class ApiRepositoryImpl : Repository {
    override suspend fun getCities(): List<City> {
        // val response = apiService.getPagedCities(page = position, limit = params.loadSize)
        TODO("Not yet implemented")
    }

}