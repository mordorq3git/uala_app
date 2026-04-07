package com.example.ualaapp.repository.implementations

import android.content.SharedPreferences
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CoordinatesEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity
import javax.inject.Inject

private const val USER_ID = "USER_ID"

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val userDao: UserDao,
    private val favouriteDao: FavouriteDao,
    private val sharedPreferences: SharedPreferences
) : DatabaseRepository {

    override suspend fun getCities() = mapCitiesEntitiesToDto(cityDao.getAll())

    override suspend fun getCity(id: Int): City {
        val cityEntity = cityDao.get(id)

        return mapCityEntityToDto(cityEntity)
    }

    override suspend fun saveCities(listOfCities: List<City>) {
        val listOfCitiesEntities = mapCitiesDtoToEntities(listOfCities)

        cityDao.refreshData(listOfCitiesEntities)
    }

    private fun mapCitiesEntitiesToDto(listOfEntities: List<CityEntity>) =
        listOfEntities.map { entity ->
            mapCityEntityToDto(entity)
        }

    private fun mapCityEntityToDto(entity: CityEntity) = City(
        _id = entity._id,
        name = entity.name,
        country = entity.country,
        coord = Coordinates(
            lat = entity.coord.lat,
            lon = entity.coord.lon
        )
    )

    private fun mapCitiesDtoToEntities(listOfDtos: List<City>) =
        listOfDtos.map { city ->
            CityEntity(
                _id = city._id,
                name = city.name,
                country = city.country,
                coord = CoordinatesEntity(
                    lat = city.coord.lat,
                    lon = city.coord.lon
                )
            )
    }

    override suspend fun saveUser(username: String) {
        var generatedId = userDao.getUserId(username)

        if(generatedId == 0L) {
            generatedId = userDao.insert(UserEntity(name = username))
        }

        addUserToSession(generatedId)
    }

    private fun addUserToSession(generatedId: Long) {
        sharedPreferences.edit().apply {
            putLong(USER_ID, generatedId)
            apply()
        }
    }

    override suspend fun getUser(username: String): User {
        val sessionId = sharedPreferences.getLong(USER_ID, -1)

        val userEntity = userDao.getUserEntity(sessionId)

        return mapUserEntityToDto(userEntity)
    }

    private fun mapUserEntityToDto(userEntity: UserEntity) = User(
        id_user = userEntity.id_user,
        name = userEntity.name
    )

}