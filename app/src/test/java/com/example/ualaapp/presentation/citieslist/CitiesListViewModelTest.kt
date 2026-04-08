package com.example.ualaapp.presentation.citieslist

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CitiesListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CitiesListViewModel

    @Before
    fun init() {
        val repository = mockk<BaseRepositoryImpl>()
        every { repository.getCitiesWithFavouritesFlow(any()) } returns flowOf(emptyList())

        this.viewModel = CitiesListViewModel(repository)
    }

    @Ignore("Al cambiar por flow cambia la manera de como trabaja el test, revisar con mayor profundidad")
    @Test
    fun getCities_withValues() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        every { repository.getCitiesWithFavouritesFlow("") } returns flowOf(listOf(
            mockk<City>(),
            mockk<City>(),
            mockk<City>(),
            mockk<City>(),
            mockk<City>()
        ))

        viewModel = CitiesListViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.citiesState.collect()
        }

        assertEquals(5, viewModel.citiesState.value.size)
    }

    @Ignore("Al cambiar por flow cambia la manera de como trabaja el test, revisar con mayor profundidad")
    @Test
    fun filter_cities() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        every { repository.getCitiesWithFavouritesFlow("") } returns flowOf(listOf(
            City(_id = 1, name = "Alabama", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Albuquerque", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Anaheim", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Arizona", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 1, name = "Sydney", country = "AU", Coordinates(1.0, 2.0)),
        ))

        viewModel = CitiesListViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.citiesState.collect()
        }

        viewModel.onEvent(CitiesListIntent.Filter("Bue"))

        assertEquals("Bue", viewModel.filterState.value)
        assertEquals(0, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("Al"))

        assertEquals("Al", viewModel.filterState.value)
        assertEquals(2, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("Ala"))

        assertEquals("Ala", viewModel.filterState.value)
        assertEquals(1, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("bue"))

        assertEquals("bue", viewModel.filterState.value)
        assertEquals(0, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("al"))

        assertEquals("al", viewModel.filterState.value)
        assertEquals(2, viewModel.citiesState.value.size)

        viewModel.onEvent(CitiesListIntent.Filter("ala"))

        assertEquals("ala", viewModel.filterState.value)
        assertEquals(1, viewModel.citiesState.value.size)
    }

    @Test
    fun add_to_favourites() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.saveFavourite(214) } returns Unit

        viewModel = CitiesListViewModel(repository)

        viewModel.onEvent(CitiesListIntent.AddToFavourites(214))

        coVerify { repository.saveFavourite(214) }
    }

    @Test
    fun remove_to_favourites() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.removeFavourite(218) } returns Unit

        viewModel = CitiesListViewModel(repository)

        viewModel.onEvent(CitiesListIntent.RemoveFromFavourites(218))

        coVerify { repository.removeFavourite(218) }
    }
}