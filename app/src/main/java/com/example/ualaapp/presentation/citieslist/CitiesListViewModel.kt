package com.example.ualaapp.presentation.citieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
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
    private val baseRepository: BaseRepositoryImpl
) : ViewModel() {
    private val _citiesState = MutableStateFlow<List<City>>(emptyList())
    val citiesState: StateFlow<List<City>> = _citiesState.asStateFlow()
    private val _filterState: MutableStateFlow<String> = MutableStateFlow("")
    val filterState: StateFlow<String> = _filterState.asStateFlow()
    private val _originalListOfCities: MutableList<City> = mutableListOf()

    fun onEvent(intent: CitiesListIntent) {
        when(intent) {
            CitiesListIntent.Get -> getCities()
            is CitiesListIntent.Filter -> filterCities(intent.filter)
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            _citiesState.update { baseRepository.getCities() }
            _originalListOfCities.addAll(_citiesState.value)
        }
    }

    private fun filterCities(filterText: String) {
        _filterState.update { filterText }

        val filteredList = _originalListOfCities.filter { city -> city.name.contains(filterText, ignoreCase = false) }

        _citiesState.update { filteredList }
    }
}