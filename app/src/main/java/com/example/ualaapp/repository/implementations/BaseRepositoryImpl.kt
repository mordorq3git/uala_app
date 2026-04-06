package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.BaseRepository
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl
) : BaseRepository {

    override suspend fun loadCities() {
        var cities = getCitiesFromDb()

        if(cities.isEmpty()) {
            cities = getCitiesFromApi()

            saveCities(cities)
        }
    }

    override suspend fun getCities() = getCitiesFromDb()

    private suspend fun getCitiesFromDb(): List<City> {
        return dataBaseRepository.getCities()
    }

    private suspend fun saveCities(listOfCities: List<City>) {
        dataBaseRepository.setCities(listOfCities)
    }

    private suspend fun getCitiesFromApi(): List<City> {
        return apiRepository.loadCities()
    }

    suspend fun setCities(listOfCities: List<City>) {
        dataBaseRepository.setCities(listOfCities)
    }
}