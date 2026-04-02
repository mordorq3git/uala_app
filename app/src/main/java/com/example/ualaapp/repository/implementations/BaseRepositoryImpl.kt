package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository

class BaseRepositoryImpl : Repository {

    override suspend fun getCities(): List<City> {
        var cities = getCitiesFromDb()

        if(cities.isEmpty()) {
            cities = getCitiesFromApi()
        }

        return cities
    }

    private suspend fun getCitiesFromDb(): List<City> {
        val dataBaseRepository = DataBaseRepositoryImpl()

        return dataBaseRepository.getCities()
    }

    private suspend fun getCitiesFromApi(): List<City> {
        val apiRepository = ApiRepositoryImpl()

        return apiRepository.getCities()
    }
}