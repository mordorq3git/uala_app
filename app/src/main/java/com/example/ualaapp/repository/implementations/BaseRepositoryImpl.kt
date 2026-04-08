package com.example.ualaapp.repository.implementations

import android.content.SharedPreferences
import com.example.ualaapp.data.City
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val USER_ID = "USER_ID"
private const val USER_ID_DEF_VALUE: Long = -999
class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl,
    private val sharedPreferences: SharedPreferences
) : BaseRepository {

    override fun getUserSessionId(): Long {
        return sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)
    }

    // Cities
    override suspend fun loadCities() {
        var cities = getCitiesFromDb()

        if(cities.isEmpty()) {
            cities = getCitiesFromApi()

            saveCities(cities)
        }
    }

    private suspend fun getCitiesFromDb() = dataBaseRepository.getCities()

    private fun getCitiesFromDb(userId: Long, query: String) = dataBaseRepository.getCitiesFilteredFlow(userId, query)

    private suspend fun getCitiesFromApi() = apiRepository.loadCities()

    override fun getCitiesWithFavouritesFlow(query: String) : Flow<List<City>> {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        return getCitiesFromDb(sessionId, query)
    }

    override fun getCityFavoritedFlow(id: Int) : Flow<City> {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        return dataBaseRepository.getCityFavoritedFlow(sessionId, id)
    }

    override suspend fun getCityFavorited(id: Int): City {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        return dataBaseRepository.getCityFavorited(sessionId, id)
    }

    private suspend fun saveCities(listOfCities: List<City>) {
        dataBaseRepository.saveCities(listOfCities)
    }

    // Users
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

    // Favourites
    override suspend fun saveFavourite(cityId: Int) {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        dataBaseRepository.saveFavourite(sessionId, cityId)
    }

    override suspend fun removeFavourite(cityId: Int) {
        val sessionId = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

        dataBaseRepository.removeFavourite(sessionId, cityId)
    }

    override fun existFavourite(userId: Long, cityId: Int) : Flow<Boolean> {
        return dataBaseRepository.existFavourite(userId, cityId)
    }
}