package com.example.ualaapp.repository.implementations

import android.util.Log
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl
) : Repository {

    override suspend fun getCities(): List<City> {
        var cities = getCitiesFromDb()

        Log.d("tag repo", "getCities: start")

        if(cities.isEmpty()) {
            Log.d("tag repo", "getCities: go to api")

            cities = getCitiesFromApi()

            saveCities(cities)

            Log.d("tag repo", "getCities: save")
        }

        Log.d("tag repo", "getCities: return")

        return cities
    }

    private suspend fun getCitiesFromDb(): List<City> {
        return dataBaseRepository.getCities()
    }

    private suspend fun saveCities(listOfCities: List<City>) {
        dataBaseRepository.setCities(listOfCities)
    }

    private suspend fun getCitiesFromApi(): List<City> {
        return apiRepository.getCities()
    }

    suspend fun setCities(listOfCities: List<City>) {
        dataBaseRepository.setCities(listOfCities)
    }
}