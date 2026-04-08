package com.example.ualaapp.presentation.map

import app.cash.turbine.test
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MapViewModel

    @Before
    fun init() {
        val repository = mockk<BaseRepositoryImpl>()

        every { repository.getUserSessionId() } returns 555

        this.viewModel = MapViewModel(repository)
    }

    @Test
    fun getCityFromDatabase() = runTest {
        val city = City(
            _id = 214,
            name = "Buenos Aires",
            country = "AR",
            coord = Coordinates(lat = 1.0, lon = 2.0)
        )

        val repository = mockk<BaseRepositoryImpl>()

        coEvery { repository.getCityFavorited(214) } returns city
        every { repository.getUserSessionId() } returns 555

        val viewModel = MapViewModel(repository)

        viewModel.currentCityState.test {
            viewModel.onEvent(MapIntent.GetCity(214))

            val currentCityState = expectMostRecentItem()!!

            assertEquals(214, currentCityState._id)
            assertEquals("Buenos Aires", currentCityState.name)
            assertEquals("AR", currentCityState.country)
            assertEquals(1.0, currentCityState.coord.lat, 0.0)
            assertEquals(2.0, currentCityState.coord.lon, 0.0)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldShowMapCardComponent() = runTest {
        viewModel.shouldShowMapCard.test {
            awaitItem()

            viewModel.onEvent(MapIntent.ShowMapCard(true))

            val shouldShowMapCard = awaitItem()

            assertTrue(shouldShowMapCard)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldHideMapCardComponent() = runTest {
        viewModel.shouldShowMapCard.test {
            awaitItem()

            //Obligo al stateflow a que cambie de estado, ya que si no detecta cambios, el test no se cumple
            viewModel.onEvent(MapIntent.ShowMapCard(true))

            awaitItem()
            viewModel.onEvent(MapIntent.ShowMapCard(false))

            val shouldShowMapCard = awaitItem()

            assertFalse(shouldShowMapCard)

            cancelAndIgnoreRemainingEvents()
        }
    }
}