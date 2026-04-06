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

    private suspend fun getCitiesFromDb() = dataBaseRepository.getCities()

    private suspend fun getCitiesFromApi() = apiRepository.loadCities()


    override suspend fun getCities() = getCitiesFromDb()
    override suspend fun getCity(id: Int) = dataBaseRepository.getCity(id)!!

    private suspend fun saveCities(listOfCities: List<City>) {
        dataBaseRepository.setCities(listOfCities)
    }
}