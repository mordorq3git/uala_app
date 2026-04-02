package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val userDao: UserDao,
    private val favouriteDao: FavouriteDao
) : Repository {
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
}