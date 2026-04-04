package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.api.CitiesApiService
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: CitiesApiService
) : Repository {
    override suspend fun getCities(): List<City> {
        return apiService.getCities()
    }

}