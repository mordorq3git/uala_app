package com.example.ualaapp.presentation.citieslist

import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.DataBaseRepositoryImpl
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
        val repository = mockk<DataBaseRepositoryImpl>()
        coEvery { repository.getCities() } returns emptyList()

        this.viewModel = CitiesListViewModel(repository)
    }

    @Test
    fun getCities_withOutValues() = runTest {
        viewModel.getCities()

        assertEquals(0, viewModel.citiesState.value.size)
    }

    @Test
    fun getCities_withValues() = runTest {
        val repository = mockk<DataBaseRepositoryImpl>()
        coEvery { repository.getCities() } returns listOf(
            mockk<City>(),
            mockk<City>(),
            mockk<City>(),
            mockk<City>(),
            mockk<City>()
        )

        viewModel = CitiesListViewModel(repository)

        viewModel.getCities()

        assertEquals(5, viewModel.citiesState.value.size)
    }
}