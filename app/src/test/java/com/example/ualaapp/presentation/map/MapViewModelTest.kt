package com.example.ualaapp.presentation.map

import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
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

        this.viewModel = MapViewModel(repository)
    }

    @Test
    fun getCityFromDatabase() {
        /*val city = City(
            _id = 214,
            name = "Buenos Aires",
            country = "AR",
            coord = Coordinates(lat = 1.0, lon = 2.0)
        )

        val repository = mockk<BaseRepositoryImpl>()

        coEvery { repository.getCity(214) } returns city

        val viewModel = MapViewModel(repository)

        viewModel.onEvent(MapIntent.GetCity(214))

        assertEquals(214, viewModel.currentCityState._id)
        assertEquals("Buenos Aires", viewModel.currentCityState.name)
        assertEquals("AR", viewModel.currentCityState.country)
        assertEquals(1.0, viewModel.currentCityState.coord.lat, 0f)
        assertEquals(2.0, viewModel.currentCityState.coord.lon, 0f)*/
    }
}