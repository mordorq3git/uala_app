package com.example.ualaapp.repository.implementations

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class ApiRepositoryImplTest {

    @Test
    fun getCities_returnsEmptyList() = runTest {
        val repository = ApiRepositoryImpl()
        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertFalse(cities.isNotEmpty())
    }
}