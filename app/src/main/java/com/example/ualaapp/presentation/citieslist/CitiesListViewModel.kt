package com.example.ualaapp.presentation.citieslist

import androidx.lifecycle.ViewModel
import com.example.ualaapp.repository.CitiesApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val apiService: CitiesApiService
) : ViewModel()