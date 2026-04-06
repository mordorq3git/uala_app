package com.example.ualaapp.presentation.citieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val baseRepository: DatabaseRepository
) : ViewModel() {
    private val _citiesState = MutableStateFlow<List<City>>(emptyList())
    val citiesState: StateFlow<List<City>> = _citiesState.asStateFlow()

    fun getCities() {
        viewModelScope.launch {
            _citiesState.update { baseRepository.getCities() }
        }
    }
}