package com.example.ualaapp.repository.implementations

import android.content.SharedPreferences
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.data.User
import com.example.ualaapp.repository.implementations.api.CitiesApiService
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
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
    @Inject lateinit var favouriteDao: FavouriteDao
    @Inject lateinit var sharedPreferences: SharedPreferences

    private val mockApiService: CitiesApiService = mockk()

    private lateinit var dataBaseRepository: DataBaseRepositoryImpl
    private lateinit var apiRepository: ApiRepositoryImpl
    private lateinit var baseRepository: BaseRepositoryImpl

    @Before
    fun init() {
        hiltRule.inject()

        dataBaseRepository = DataBaseRepositoryImpl(cityDao, userDao, favouriteDao, sharedPreferences)
        apiRepository = ApiRepositoryImpl(mockApiService)

        baseRepository = BaseRepositoryImpl(
            apiRepository = apiRepository,
            dataBaseRepository = dataBaseRepository
        )
    }

    @Test
    fun getCities_returnsEmptyList() = runTest {
        coEvery { apiRepository.loadCities() } returns emptyList()

        val cities = baseRepository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertTrue(cities.isEmpty())
    }

    @Test
    fun getCities_returnsEmptyList_from_db() = runTest {
        val mockedCity = mockk<City>(relaxed = true)
        val listOfCities = listOf(
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity
        )
        coEvery { apiRepository.loadCities() } returns listOfCities

        baseRepository.loadCities()

        val cities = baseRepository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isEmpty())
        Assert.assertEquals(6, cities.size)
    }

    @Test
    fun getCities_returnsEmptyList_from_db_and_api() = runTest {
        val mockedCityEntity = mockk<CityEntity>(relaxed = true)
        for (n in 1 .. 5) cityDao.insert(mockedCityEntity)

        dataBaseRepository = DataBaseRepositoryImpl(cityDao, userDao, favouriteDao, sharedPreferences)

        baseRepository = BaseRepositoryImpl(
            apiRepository = apiRepository,
            dataBaseRepository = dataBaseRepository
        )

        val cities = baseRepository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isEmpty())
        Assert.assertEquals(5, cities.size)
    }

    @Test
    fun setCities_toDb() = runTest {
        val mockedCity = mockk<City>(relaxed = true)
        val listOfCities = listOf(
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity
        )

        coEvery { apiRepository.loadCities() } returns listOfCities

        baseRepository.loadCities()

        val cities = baseRepository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isEmpty())
        Assert.assertEquals(6, cities.size)
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

        val cities = baseRepository.getCities()

        Assert.assertFalse(cities.isEmpty())
        Assert.assertEquals(3, cities.size)

        val cityFromDb = baseRepository.getCity(216)

        Assert.assertNotNull(cityFromDb)
        Assert.assertEquals("Salta", cityFromDb.name)
        Assert.assertEquals("AR", cityFromDb.country)
    }

    @Test
    fun saveUser_toDb() = runTest {
        baseRepository.saveUser("username")

        val user: User = baseRepository.getUser("username")

        Assert.assertEquals("username", user.name)
    }
}