package com.example.ualaapp.repository.implementations

import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
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
class DataBaseRepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Inject
    lateinit var repository: DataBaseRepositoryImpl

    @Inject
    lateinit var cityDao: CityDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun getCities_returnsEmptyList() = runTest {
        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertTrue(cities.isEmpty())
    }

    @Test
    fun getCities_returnsNotEmptyList() = runTest {
        val mockedCityEntity = mockk<CityEntity>(relaxed = true)
        for (n in 1 .. 5) cityDao.insert(mockedCityEntity)

        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isEmpty())
        Assert.assertEquals(5, cities.size)
    }
}