package com.example.ualaapp.presentation.loading

import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadingAndRegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoadingAndRegisterViewModel

    @Before
    fun init() {
        val repository = mockk<BaseRepositoryImpl>()
        coEvery { repository.getCities() } returns emptyList()

        this.viewModel = LoadingAndRegisterViewModel(repository)
    }

    @Test
    fun onEvent_Loading() = runTest {
        viewModel.onEvent(LoadingIntent.Load)

        assertTrue(viewModel.loadingAndRegistryUIState.value == LoadingAndRegistryUIState.Success)
    }

    @Test
    fun onEvent_UpdateUserName() {
        viewModel.onEvent(RegistryIntent.SetUserName("username"))

        assertEquals("username", viewModel.registerUserValue.value)

        viewModel.onEvent(RegistryIntent.SetUserName(""))

        assertEquals("", viewModel.registerUserValue.value)

        viewModel.onEvent(RegistryIntent.SetUserName("other_username"))

        assertEquals("other_username", viewModel.registerUserValue.value)
    }

    @Test
    fun onEvent_Registry() {
        viewModel.onEvent(RegistryIntent.SetUserName("username"))

        assertEquals("username", viewModel.registerUserValue.value)
    }
}