package com.example.ualaapp.repository.implementations

import android.content.SharedPreferences
import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.BaseRepository
import javax.inject.Inject
import kotlin.apply

private const val USER_ID = "USER_ID"
private const val USER_ID_DEF_VALUE: Long = -999
class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl,
    private val sharedPreferences: SharedPreferences
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
    override suspend fun getCity(id: Int) = dataBaseRepository.getCity(id)

    private suspend fun saveCities(listOfCities: List<City>) {
        dataBaseRepository.saveCities(listOfCities)
    }

    override suspend fun saveUser(username: String) {
        val idUser = dataBaseRepository.saveUser(username)

        addUserToSession(idUser)
    }

    override suspend fun getUser(username: String) : User {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        return dataBaseRepository.getUser(sessionId)
    }

    private fun addUserToSession(generatedId: Long) {
        sharedPreferences.edit().apply {
            putLong(USER_ID, generatedId)
            apply()
        }
    }

    override suspend fun saveFavourite(cityId: Int) {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        dataBaseRepository.saveFavourite(sessionId, cityId)
    }

    override suspend fun removeFavourite(cityId: Int) {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        dataBaseRepository.removeFavourite(sessionId, cityId)
    }
}