package com.example.ualaapp.presentation.loading

import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadingAndRegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Test
    fun onEvent_Loading() = runTest {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.getCities() } returns emptyList()

        val viewModel = LoadingAndRegisterViewModel(repository)

        viewModel.onEvent(LoadingIntent.Load)

        assertTrue(viewModel.loadingAndRegistryUIState.value == LoadingAndRegistryUIState.Success)
    }
}