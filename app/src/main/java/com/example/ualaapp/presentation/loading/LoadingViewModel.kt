package com.example.ualaapp.presentation.loading

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.data.City
import com.example.ualaapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val baseRepository: Repository
) : ViewModel() {
    private val _citiesState = MutableStateFlow<List<City>>(emptyList())
    val citiesState: StateFlow<List<City>> = _citiesState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            Log.d("TAG", "loadData: comienzo")

            _citiesState.update { baseRepository.getCities() }

            Log.d("TAG", "loadData: fin")
        }
    }
}