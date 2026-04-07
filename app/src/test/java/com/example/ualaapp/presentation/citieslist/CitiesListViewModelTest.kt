package com.example.ualaapp.presentation.citieslist

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CitiesListViewModel

    @Before
    fun init() {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.getCities() } returns emptyList()

        this.viewModel = CitiesListViewModel(repository)
    }

    @Test
    fun getCities_withOutValues() = runTest {
        viewModel.onEvent(CitiesListIntent.Get)

        assertEquals(0, viewModel.citiesState.value.size)
    }

    @Test
    fun getCities_withValues() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.getCities() } returns listOf(
            mockk<City>(),
            mockk<City>(),
            mockk<City>(),
            mockk<City>(),
            mockk<City>()
        )

        viewModel = CitiesListViewModel(repository)

        viewModel.onEvent(CitiesListIntent.Get)

        assertEquals(5, viewModel.citiesState.value.size)
    }

    @Test
    fun filter_cities() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.getCities() } returns listOf(
            City(_id = 1, name = "Alabama", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Albuquerque", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Anaheim", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Arizona", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Sydney", country = "AU", Coordinates(1.0, 2.0)),
        )

        viewModel = CitiesListViewModel(repository)

        viewModel.onEvent(CitiesListIntent.Get)

        viewModel.onEvent(CitiesListIntent.Filter("Bue"))

        assertEquals("Bue", viewModel.filterState.value)
        assertEquals(0, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("Al"))

        assertEquals("Al", viewModel.filterState.value)
        assertEquals(2, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("Ala"))

        assertEquals("Ala", viewModel.filterState.value)
        assertEquals(1, viewModel.citiesState.value.size)
    }
}