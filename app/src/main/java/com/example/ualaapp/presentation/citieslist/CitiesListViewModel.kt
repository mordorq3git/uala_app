package com.example.ualaapp.presentation.citieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val baseRepository: BaseRepositoryImpl
) : ViewModel() {
    private val _filterState = MutableStateFlow("")
    val filterState: StateFlow<String> = _filterState.asStateFlow()
    val citiesState: StateFlow<List<City>> = _filterState
        .flatMapLatest { query ->
            baseRepository.getCitiesWithFavouritesFlow(query)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onEvent(intent: CitiesListIntent) {
        when(intent) {
            is CitiesListIntent.Filter -> filterCities(intent.filter)
            is CitiesListIntent.AddToFavourites -> addToFavourites(intent._id)
            is CitiesListIntent.RemoveFromFavourites -> removeFromFavourites(intent._id)
        }
    }

    private fun filterCities(filterText: String) {
        _filterState.update { filterText }
    }

    private fun addToFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.saveFavourite(cityId)
        }
    }

    private fun removeFromFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.removeFavourite(cityId)
        }
    }
}