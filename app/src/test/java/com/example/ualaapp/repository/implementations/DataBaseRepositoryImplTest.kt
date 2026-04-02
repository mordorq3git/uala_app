package com.example.ualaapp.repository.implementations

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class DataBaseRepositoryImplTest {

    @Test
    fun getCities_returnsEmptyList() = runTest {
        val repository = DataBaseRepositoryImpl()
        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertTrue(cities.isEmpty())
    }
}