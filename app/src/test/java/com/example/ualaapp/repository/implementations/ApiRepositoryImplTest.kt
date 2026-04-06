package com.example.ualaapp.repository.implementations

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.api.CitiesApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ApiRepositoryImplTest {
    private val apisService: CitiesApiService = mockk()
    private lateinit var repository: ApiRepositoryImpl

    @Before
    fun init() {
        repository = ApiRepositoryImpl(apisService)
    }

    @Test
    fun getCities_returnsEmptyList() = runTest {
        coEvery { apisService.getCities() } returns emptyList()

        val cities = repository.loadCities()

        Assert.assertNotNull(cities)
        Assert.assertTrue(cities.isEmpty())
    }

    @Test
    fun getCities_returnsNotEmptyList() = runTest {
        val mockedCity = mockk<City>(relaxed = true)
        val listOfCities = listOf(
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity,
            mockedCity
        )

        coEvery { apisService.getCities() } returns listOfCities

        val cities = repository.loadCities()

        Assert.assertNotNull(cities)
        Assert.assertTrue(cities.isNotEmpty())
        Assert.assertEquals(6, cities.size)
    }
}