package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CoordinatesEntity
import com.example.ualaapp.repository.implementations.database.entities.FavouriteEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity
import javax.inject.Inject

private const val USER_ID = "USER_ID"

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val userDao: UserDao,
    private val favouriteDao: FavouriteDao
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

    override suspend fun saveUser(username: String) : Long {
        var generatedId = userDao.getUserId(username)

        if(generatedId == 0L) {
            generatedId = userDao.insert(UserEntity(name = username))
        }

        return generatedId
    }

    override suspend fun getUser(id: Long): User {
        val userEntity = userDao.getUserEntity(id)

        return mapUserEntityToDto(userEntity)
    }

    private fun mapUserEntityToDto(userEntity: UserEntity) = User(
        id_user = userEntity.id_user,
        name = userEntity.name
    )

    override suspend fun saveFavourite(idUser: Long, city_id: Int) {
        val favouriteEntity = FavouriteEntity(
            id_user = idUser,
            _id = city_id
        )
        favouriteDao.insert(favouriteEntity)
    }

    override suspend fun removeFavourite(id: Int) {
        TODO("Not yet implemented")
    }
}