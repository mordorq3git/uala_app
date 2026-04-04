package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl
) : Repository {

    override suspend fun getCities(): List<City> {
        var cities = getCitiesFromDb()

        if(cities.isEmpty()) {
            cities = getCitiesFromApi()
        }

        return cities
    }

    private suspend fun getCitiesFromDb(): List<City> {
        return dataBaseRepository.getCities()
    }

    private suspend fun getCitiesFromApi(): List<City> {
        return apiRepository.getCities()
    }

    suspend fun setCities(listOfCities: List<City>) {
        dataBaseRepository.setCities(listOfCities)
    }
}