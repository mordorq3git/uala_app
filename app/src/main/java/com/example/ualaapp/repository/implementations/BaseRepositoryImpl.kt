package com.example.ualaapp.repository.implementations

import android.content.SharedPreferences
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.BaseRepository
import javax.inject.Inject

private const val USER_ID = "USER_ID"
private const val USER_ID_DEF_VALUE: Long = -999
class BaseRepositoryImpl @Inject constructor(
    private val apiRepository: ApiRepositoryImpl,
    private val dataBaseRepository: DataBaseRepositoryImpl,
    private val sharedPreferences: SharedPreferences
) : BaseRepository {

    // Cities
    override suspend fun loadCities() {
        var cities = getCitiesFromDb()

        if(cities.isEmpty()) {
            cities = getCitiesFromApi()

            saveCities(cities)
        }
    }

    private suspend fun getCitiesFromDb() = dataBaseRepository.getCities()

    private fun getCitiesFromDbFilteredFlow(userId: Long, query: String) = dataBaseRepository.getCitiesFilteredFlow(userId, query)

    private suspend fun getCitiesFromApi() = apiRepository.loadCities()

    private suspend fun saveCities(listOfCities: List<City>) {
        dataBaseRepository.saveCities(listOfCities)
    }

    // Users
    override fun getUserSessionId() = sharedPreferences.getLong(USER_ID, USER_ID_DEF_VALUE)

    override suspend fun saveUser(username: String) {
        val idUser = dataBaseRepository.saveUser(username)

        addUserToSession(idUser)
    }

    private fun addUserToSession(generatedId: Long) {
        sharedPreferences.edit().apply {
            putLong(USER_ID, generatedId)
            apply()
        }
    }

    // Favourites
    override suspend fun saveFavourite(cityId: Int) {
        dataBaseRepository.saveFavourite(getUserSessionId(), cityId)
    }

    override fun getCitiesWithFavouritesFlow(query: String) = getCitiesFromDbFilteredFlow(getUserSessionId(), query)

    override suspend fun getCityFavorited(cityId: Int) = dataBaseRepository.getCityFavorited(getUserSessionId(), cityId)

    override suspend fun removeFavourite(cityId: Int) {
        dataBaseRepository.removeFavourite(getUserSessionId(), cityId)
    }

    override fun existFavourite(userId: Long, cityId: Int) = dataBaseRepository.existFavourite(userId, cityId)
}