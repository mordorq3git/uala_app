package com.example.ualaapp.repository.implementations

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class BaseRepositoryImplTest {

    @Test
    fun getCities_returnsEmptyList() = runTest {
        val repository = BaseRepositoryImpl()
        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isNotEmpty())
    }

    @Test
    fun getCities_returnsEmptyList_from_db() = runTest {
        val repository = BaseRepositoryImpl()
        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isNotEmpty())
    }
}