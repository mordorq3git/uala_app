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
    private val _listenerCityState: MutableStateFlow<City?> = MutableStateFlow(null)
    val listenerCityState: StateFlow<City?> = _listenerCityState.asStateFlow()

    fun onEvent(intent: MapIntent) {
        when(intent) {
            is MapIntent.ListenerState -> listenerState(intent.id)
            is MapIntent.GetCity -> getCity(intent.id)
            is MapIntent.AddToFavourites -> addToFavourites(intent._id)
            is MapIntent.RemoveFromFavourites -> removeFromFavourites(intent._id)
        }
    }

    private fun listenerState(id: Int) {
        viewModelScope.launch {
            baseRepository.getCityFavoritedFlow(id).collect { city ->
                _listenerCityState.update { city }
            }
        }
    }

    private fun getCity(id: Int) {
        viewModelScope.launch {
            _currentCityState.update {
                baseRepository.getCityFavorited(id)
            }
        }
    }

    private fun addToFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.saveFavourite(cityId)

            _currentCityState.update { it!!.copy(isFavourite = true) }
        }
    }

    private fun removeFromFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.removeFavourite(cityId)

            _currentCityState.update { it!!.copy(isFavourite = false) }
        }
    }
}