package com.example.ualaapp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
    private val baseRepository: BaseRepositoryImpl
) : ViewModel() {
    private val _currentCityState: MutableStateFlow<City?> = MutableStateFlow(null)
    val currentCityState: StateFlow<City?> = _currentCityState.asStateFlow()

    fun onEvent(intent: MapIntent) {
        when(intent) {
            is MapIntent.GetCity -> getCity(intent.id)
            is MapIntent.AddToFavourites -> addToFavourites(intent._id)
            is MapIntent.RemoveFromFavourites -> removeFromFavourites(intent._id)
        }
    }

    private fun getCity(id: Int) {
        viewModelScope.launch {
            baseRepository.getCity(id).collect { city ->
                _currentCityState.update { city }
            }
        }
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