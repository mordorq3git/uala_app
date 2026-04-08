package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.CityWithFavorite
import com.example.ualaapp.repository.implementations.database.entities.CoordinatesEntity
import com.example.ualaapp.repository.implementations.database.entities.FavouriteEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val userDao: UserDao,
    private val favouriteDao: FavouriteDao
) : DatabaseRepository {

    override suspend fun getCities() = mapCitiesEntitiesToDto(cityDao.getAll())

    override fun getCitiesFilteredFlow(userId: Long, query: String) : Flow<List<City>> {
        return cityDao.getCitiesFilteredFlow(userId, query).map { list ->
            mapCitiesWithFavoriteToDto(list)
        }.flowOn(Dispatchers.Default)
    }

    override fun getCityFavoritedFlow(userId: Long, id: Int): Flow<City> {
        val cityEntity = cityDao.getCityFavoritedFlow(userId, id)

        return cityEntity.map {
            entity -> mapCityEntityToDto(entity)
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getCityFavorited(userId: Long, id: Int): City {
        val cityEntity = cityDao.getCityFavorited(userId, id)

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

    private fun mapCitiesWithFavoriteToDto(listOfCitiesWithFavourites: List<CityWithFavorite>) =
        listOfCitiesWithFavourites.map { entity ->
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
        isFavourite = false
    )

    private fun mapCityEntityToDto(cityWithFavorite: CityWithFavorite) = City(
        _id = cityWithFavorite.city._id,
        name = cityWithFavorite.city.name,
        country = cityWithFavorite.city.country,
        coord = Coordinates(
            lat = cityWithFavorite.city.coord.lat,
            lon = cityWithFavorite.city.coord.lon
        ),
        isFavourite = cityWithFavorite.isFavorite
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

    override suspend fun saveFavourite(userId: Long, cityId: Int) {
        val favouriteEntity = FavouriteEntity(
            id_user = userId,
            _id = cityId
        )
        favouriteDao.insert(favouriteEntity)
    }

    override suspend fun removeFavourite(userId: Long, cityId: Int) {
        favouriteDao.delete(
            id_user = userId,
            city_id = cityId
        )
    }
}
