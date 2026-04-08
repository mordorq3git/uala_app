package com.example.ualaapp.repository.implementations

import android.content.SharedPreferences
import app.cash.turbine.test
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.api.CitiesApiService
import com.example.ualaapp.repository.implementations.database.AppDatabase
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavoriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class BaseRepositoryImplTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Inject lateinit var cityDao: CityDao
    @Inject lateinit var userDao: UserDao
    @Inject lateinit var favoriteDao: FavoriteDao
    @Inject lateinit var sharedPreferences: SharedPreferences
    @Inject lateinit var appDatabase: AppDatabase

    private val mockApiService: CitiesApiService = mockk()

    private lateinit var dataBaseRepository: DataBaseRepositoryImpl
    private lateinit var apiRepository: ApiRepositoryImpl
    private lateinit var baseRepository: BaseRepositoryImpl

    @Before
    fun init() {
        hiltRule.inject()

        appDatabase.clearAllTables()
        dataBaseRepository = DataBaseRepositoryImpl(cityDao, userDao, favoriteDao)
        apiRepository = ApiRepositoryImpl(mockApiService)

        baseRepository = BaseRepositoryImpl(
            apiRepository = apiRepository,
            dataBaseRepository = dataBaseRepository,
            sharedPreferences = sharedPreferences
        )
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun getCitiesWithFavoritesFlow_returnsEmptyList() = runTest {
        coEvery { apiRepository.loadCities() } returns emptyList()

        baseRepository.loadCities()

        baseRepository.getCitiesWithFavoritesFlow("").test {
            val cities = awaitItem()

            Assert.assertNotNull(cities)
            Assert.assertTrue(cities.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCitiesWithFavoritesFlow_returnsEmptyList_from_db() = runTest {
        val listOfMockedCities = List(6) { mockk<City>(relaxed = true) }

        coEvery { apiRepository.loadCities() } returns listOfMockedCities

        baseRepository.loadCities()

        baseRepository.getCitiesWithFavoritesFlow("").test {
            var cities = awaitItem()

            if (cities.isEmpty()) {
                cities = awaitItem()
            }

            Assert.assertNotNull(cities)
            Assert.assertFalse(cities.isEmpty())
            Assert.assertEquals(6, cities.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCitiesWithFavoritesFlow_returnsEmptyList_fromDbAndApi_with_insertAll() = runTest {
        val listOfMockedCityEntities = List(8) { mockk<CityEntity>(relaxed = true) }
        cityDao.insertAll(listOfMockedCityEntities)

        dataBaseRepository = DataBaseRepositoryImpl(cityDao, userDao, favoriteDao)

        baseRepository = BaseRepositoryImpl(
            apiRepository = apiRepository,
            dataBaseRepository = dataBaseRepository,
            sharedPreferences = sharedPreferences
        )

        baseRepository.getCitiesWithFavoritesFlow("").test {
            val cities = awaitItem()

            Assert.assertNotNull(cities)
            Assert.assertFalse(cities.isEmpty())
            Assert.assertEquals(8, cities.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCitiesWithFavoritesFlow_returnsEmptyList_fromDbAndApi_With_isolatedInsert() = runTest {
        val mockedCityEntity = mockk<CityEntity>(relaxed = true)
        for (n in 1 .. 5) cityDao.insert(mockedCityEntity)

        dataBaseRepository = DataBaseRepositoryImpl(cityDao, userDao, favoriteDao)

        baseRepository = BaseRepositoryImpl(
            apiRepository = apiRepository,
            dataBaseRepository = dataBaseRepository,
            sharedPreferences = sharedPreferences
        )

        baseRepository.getCitiesWithFavoritesFlow("").test {
            val cities = awaitItem()

            Assert.assertNotNull(cities)
            Assert.assertFalse(cities.isEmpty())
            Assert.assertEquals(5, cities.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun setCities_toDb() = runTest {
        val listOfMockedCities = List(6) { mockk<City>(relaxed = true) }

        coEvery { apiRepository.loadCities() } returns listOfMockedCities

        baseRepository.loadCities()

        baseRepository.getCitiesWithFavoritesFlow("").test {
            val cities = awaitItem()

            Assert.assertNotNull(cities)
            Assert.assertFalse(cities.isEmpty())
            Assert.assertEquals(6, cities.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCity_fromDb() = runTest {
        val city = City(
            _id = 214,
            name = "Buenos Aires",
            country = "AR",
            coord = Coordinates(lat = 1.0, lon = 2.0)
        )

        val listOfCities = listOf(
            city,
            city.copy(_id = 215, name = "Chubut"),
            city.copy(_id = 216, name = "Salta")
        )

        coEvery { apiRepository.loadCities() } returns listOfCities

        baseRepository.loadCities()

        baseRepository.getCitiesWithFavoritesFlow("").test {
            val cities = awaitItem()

            Assert.assertFalse(cities.isEmpty())
            Assert.assertEquals(3, cities.size)

            val cityFromDb = baseRepository.getCityFavorited(216)

            Assert.assertNotNull(cityFromDb)
            Assert.assertEquals("Salta", cityFromDb.name)
            Assert.assertEquals("AR", cityFromDb.country)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun saveFavorite_toDb() = runTest {
        val city = City(
            _id = 214,
            name = "Buenos Aires",
            country = "AR",
            coord = Coordinates(lat = 1.0, lon = 2.0)
        )

        val listOfCities = listOf(
            city,
            city.copy(_id = 215, name = "Chubut"),
            city.copy(_id = 216, name = "Salta")
        )

        coEvery { apiRepository.loadCities() } returns listOfCities

        baseRepository.loadCities()

        baseRepository.saveUser("username")

        baseRepository.saveFavorite(214)
    }

    @Test
    fun removeFavorite_toDb() = runTest {
        val city = City(
            _id = 214,
            name = "Buenos Aires",
            country = "AR",
            coord = Coordinates(lat = 1.0, lon = 2.0)
        )

        val listOfCities = listOf(
            city,
            city.copy(_id = 215, name = "Chubut"),
            city.copy(_id = 216, name = "Salta")
        )

        coEvery { apiRepository.loadCities() } returns listOfCities

        baseRepository.loadCities()

        baseRepository.saveUser("username")

        baseRepository.removeFavorite(214)
    }
}