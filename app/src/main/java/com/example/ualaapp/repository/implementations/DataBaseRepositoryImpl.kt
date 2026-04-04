package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CoordinatesEntity
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val userDao: UserDao,
    private val favouriteDao: FavouriteDao
) : DatabaseRepository {
    override suspend fun getCities(): List<City> {
        return cityDao.getAll().map { entity ->
            City(
                _id = entity._id,
                name = entity.name,
                country = entity.country,
                coord = Coordinates(
                    lat = entity.coord.lat,
                    lon = entity.coord.lon
                )
            )
        }
    }

    override suspend fun setCities(listOfCities: List<City>) {
        val listOfCitiesEntities = listOfCities.map { city ->
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

        cityDao.refreshData(listOfCitiesEntities)
    }
}