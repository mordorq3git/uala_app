package com.example.ualaapp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    private val baseRepository: BaseRepositoryImpl
) : ViewModel() {
    private val _currentCityState: MutableStateFlow<City?> = MutableStateFlow(null)
    val currentCityState: StateFlow<City?> = _currentCityState.asStateFlow()
    private val _currentCityId = MutableStateFlow(-999)
    private val _currentUserId = MutableStateFlow(baseRepository.getUserSessionId())

    val existFavourite: StateFlow<Boolean> = combine(_currentCityId, _currentUserId) { cityId, userId ->
        if (cityId != -999 && userId != -999L) {
            baseRepository.existFavourite(userId, cityId)
        } else {
            flowOf(false)
        }
    }.flatMapLatest { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )


    fun onEvent(intent: MapIntent) {
        when(intent) {
            is MapIntent.GetCity -> getCity(intent.id)
            is MapIntent.AddToFavourites -> addToFavourites(intent._id)
            is MapIntent.RemoveFromFavourites -> removeFromFavourites(intent._id)
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
        }
    }

    private fun removeFromFavourites(cityId: Int) {
        viewModelScope.launch {
            baseRepository.removeFavourite(cityId)
        }
    }

    fun checkFavouriteStatus(cityId: Int) {
        _currentCityId.update { cityId }
    }
}