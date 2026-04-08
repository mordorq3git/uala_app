package com.example.ualaapp.presentation.citieslist

import app.cash.turbine.test
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
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
        every { repository.getCitiesWithFavoritesFlow(any()) } returns flowOf(emptyList())

        this.viewModel = CitiesListViewModel(repository)
    }

    @Test
    fun getCities_withValues() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        val listOfMockedCities = List(5) { mockk<City>() }
        every { repository.getCitiesWithFavoritesFlow("") } returns flowOf(listOfMockedCities)

        viewModel = CitiesListViewModel(repository)

        viewModel.citiesState.test {
            advanceUntilIdle()

            val actualCities = expectMostRecentItem()
            assertEquals(5, actualCities.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun filter_cities() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        val cities = listOf(
            City(_id = 1, name = "Alabama", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 2, name = "Albuquerque", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 3, name = "Anaheim", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 4, name = "Arizona", country = "US", Coordinates(1.0, 2.0)),
            City(_id = 5, name = "Sydney", country = "AU", Coordinates(1.0, 2.0)),
        )

        every { repository.getCitiesWithFavoritesFlow(any()) } answers {
            val query = firstArg<String>()
            flowOf(cities.filter { it.name.startsWith(query, ignoreCase = true) })
        }

        val viewModel = CitiesListViewModel(repository)

        viewModel.citiesState.test {
            var initialItems = awaitItem()

            if (initialItems.isEmpty()) {
                initialItems = awaitItem()
            }

            assertEquals(5, initialItems.size)

            viewModel.onEvent(CitiesListIntent.Filter("Bue"))
            val bueItems = awaitItem()
            assertEquals(0, bueItems.size)
            assertEquals("Bue", viewModel.filterState.value)

            viewModel.onEvent(CitiesListIntent.Filter("Al"))
            val alItems = awaitItem()
            assertEquals(2, alItems.size)
            assertEquals("Al", viewModel.filterState.value)
            assertEquals("Alabama", alItems[0].name)
            assertEquals("Albuquerque", alItems[1].name)

            viewModel.onEvent(CitiesListIntent.Filter("Ala"))
            val alaItems = awaitItem()
            assertEquals(1, alaItems.size)
            assertEquals("Ala", viewModel.filterState.value)
            assertEquals("Alabama", alaItems[0].name)

            viewModel.onEvent(CitiesListIntent.Filter("bue"))
            val bueItems2 = awaitItem()
            assertEquals(0, bueItems2.size)
            assertEquals("bue", viewModel.filterState.value)

            viewModel.onEvent(CitiesListIntent.Filter("al"))
            val alItems2 = awaitItem()
            assertEquals(2, alItems2.size)
            assertEquals("al", viewModel.filterState.value)
            assertEquals("Alabama", alItems2[0].name)
            assertEquals("Albuquerque", alItems2[1].name)

            viewModel.onEvent(CitiesListIntent.Filter("ala"))
            val alaItems2 = awaitItem()
            assertEquals(1, alaItems2.size)
            assertEquals("ala", viewModel.filterState.value)
            assertEquals("Alabama", alaItems2[0].name)

            viewModel.onEvent(CitiesListIntent.Filter("s"))
            val sItems = awaitItem()
            assertEquals(1, sItems.size)
            assertEquals("s", viewModel.filterState.value)
            assertEquals("Sydney", sItems[0].name)

            viewModel.onEvent(CitiesListIntent.Filter("syd"))
            assertEquals("syd", viewModel.filterState.value)
            assertEquals("Sydney", viewModel.citiesState.value[0].name)

            viewModel.onEvent(CitiesListIntent.Filter("ari"))
            val ariItems = awaitItem()
            assertEquals(1, ariItems.size)
            assertEquals("ari", viewModel.filterState.value)
            assertEquals("Arizona", ariItems[0].name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun add_to_favorites() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.saveFavorite(214) } returns Unit

        viewModel = CitiesListViewModel(repository)

        viewModel.onEvent(CitiesListIntent.AddToFavorites(214))

        coVerify { repository.saveFavorite(214) }
    }

    @Test
    fun remove_to_favorites() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.removeFavorite(218) } returns Unit

        viewModel = CitiesListViewModel(repository)

        viewModel.onEvent(CitiesListIntent.RemoveFromFavorites(218))

        coVerify { repository.removeFavorite(218) }
    }
}
