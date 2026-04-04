package com.example.ualaapp.presentation.citieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.api.CitiesApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val baseRepository: Repository
) : ViewModel() {

    fun loadData() {
        viewModelScope.launch {
            Log.d("TAG", "loadData: comienzo")

            val cities = baseRepository.getCities()

            Log.d("TAG", "loadData: cities: $cities")
            Log.d("TAG", "loadData: fin")
        }
    }
}