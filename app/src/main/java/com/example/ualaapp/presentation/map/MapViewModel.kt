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
        }
    }

    private fun getCity(id: Int) {
        viewModelScope.launch {
            val city = baseRepository.getCity(id)

            _currentCityState.update { city }
        }
    }

}