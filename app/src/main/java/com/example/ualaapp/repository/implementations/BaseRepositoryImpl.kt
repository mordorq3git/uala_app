package com.example.ualaapp.repository.implementations

import android.util.Log
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository
import javax.inject.Inject
import kotlin.time.measureTime

class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl
) : Repository {

    override suspend fun getCities(): List<City> {
        Log.d("BaseRepoLog", "--> consulta a db")
        var cities = getCitiesFromDb()

        if(cities.isEmpty()) {
            Log.d("BaseRepoLog", "--> db vacia")

            val tiempo = measureTime {
                cities = getCitiesFromApi()
            }

            Log.d("BaseRepoLog", "--> La api tardó: $tiempo")

            val tiempo2 = measureTime {
                saveCities(cities)
            }

            Log.d("BaseRepoLog", "--> La api tardó: $tiempo --> La DB tardó: $tiempo2, ")
        }
        Log.d("BaseRepoLog", "--> retorno")
        /*if(cities.isEmpty()) {
            cities = getCitiesFromApi()

            saveCities(cities)
        }*/

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