package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavoriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite
import com.example.ualaapp.repository.implementations.database.entities.CoordinatesEntity
import com.example.ualaapp.repository.implementations.database.entities.FavoriteEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val userDao: UserDao,
    private val favoriteDao: FavoriteDao
) : DatabaseRepository {
    // Cities
    override suspend fun getCities() = mapCitiesEntitiesToDto(cityDao.getAll())

    override fun getCitiesFilteredFlow(userId: Long, query: String) : Flow<List<City>> {
        return cityDao.getCitiesFilteredFlow(userId, query).map { list ->
            mapCitiesWithFavoriteToDto(list)
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun saveCities(listOfCities: List<City>) {
        val listOfCitiesEntities = mapCitiesDtoToEntities(listOfCities)

        cityDao.refreshData(listOfCitiesEntities)
    }

    private fun mapCitiesEntitiesToDto(listOfEntities: List<CityEntity>) =
        listOfEntities.map { entity ->
            mapCityEntityToDto(entity)
        }

    private fun mapCitiesWithFavoriteToDto(listOfCitiesWithFavorites: List<CityWithFavorite>) =
        listOfCitiesWithFavorites.map { entity ->
            mapCityEntityToDto(entity)
        }

    private fun mapCityEntityToDto(entity: CityEntity) = City(
        _id = entity._id,
        name = entity.name,
        country = entity.country,
        coord = Coordinates(
            lat = entity.coord.lat,
            lon = entity.coord.lon
        ),
        isFavorite = false
    )

    private fun mapCityEntityToDto(cityWithFavorite: CityWithFavorite) = City(
        _id = cityWithFavorite.city._id,
        name = cityWithFavorite.city.name,
        country = cityWithFavorite.city.country,
        coord = Coordinates(
            lat = cityWithFavorite.city.coord.lat,
            lon = cityWithFavorite.city.coord.lon
        ),
        isFavorite = cityWithFavorite.isFavorite
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

    // Users
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

    // Favorites
    override suspend fun saveFavorite(userId: Long, cityId: Int) {
        val favoriteEntity = FavoriteEntity(
            id_user = userId,
            _id = cityId
        )
        favoriteDao.insert(favoriteEntity)
    }

    override suspend fun getCityFavorited(userId: Long, id: Int): City {
        val cityEntity = favoriteDao.getCityFavorited(userId, id)

        return mapCityEntityToDto(cityEntity)
    }

    override suspend fun removeFavorite(userId: Long, cityId: Int) {
        favoriteDao.delete(
            id_user = userId,
            city_id = cityId
        )
    }

    override fun existFavorite(userId: Long, cityId: Int) : Flow<Boolean> {
        val existFavorite = favoriteDao.existFavorite(
            id_user = userId,
            city_id = cityId
        )

        return existFavorite
    }
}
