package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository

class BaseRepositoryImpl : Repository {

    override suspend fun getCities(): List<City> {
        val dataBaseRepository = DataBaseRepositoryImpl()

        val cities = dataBaseRepository.getCities()

        return cities
    }
}