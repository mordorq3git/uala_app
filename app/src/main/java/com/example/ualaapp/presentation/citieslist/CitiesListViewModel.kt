package com.example.ualaapp.presentation.citieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val baseRepository: BaseRepositoryImpl
) : ViewModel() {
    private val _citiesState = MutableStateFlow<List<City>>(emptyList())
    private val _filterState = MutableStateFlow("")
    val filterState: StateFlow<String> = _filterState.asStateFlow()
    val citiesState: StateFlow<List<City>> = combine(_citiesState, _filterState) { cities, query ->
        if (query.isBlank()) {
            cities
        } else {
            cities.filter { city ->
                city.name.startsWith(
                    query,
                    ignoreCase = true
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = emptyList()
    )

    fun onEvent(intent: CitiesListIntent) {
        when(intent) {
            CitiesListIntent.Get -> getCities()
            is CitiesListIntent.Filter -> filterCities(intent.filter)
            is CitiesListIntent.AddToFavourites -> addToFavourites(intent._id)
            is CitiesListIntent.RemoveFromFavourites -> removeFromFavourites(intent._id)
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            _citiesState.update { baseRepository.getCitiesWithFavourites() }
        }
    }

    private fun filterCities(filterText: String) {
        _filterState.update { filterText }
    }

    private fun addToFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.saveFavourite(cityId)

            updateFavouriteStatus(cityId, true)
        }
    }

    private fun removeFromFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.removeFavourite(cityId)

            updateFavouriteStatus(cityId, false)
        }
    }

    private fun updateFavouriteStatus(cityId: Int, isFavourite: Boolean) {
        _citiesState.update { currentCities ->
            currentCities.map { city ->
                if (city._id == cityId) {
                    city.copy(isFavourite = isFavourite)
                } else {
                    city
                }
            }
        }
    }
}