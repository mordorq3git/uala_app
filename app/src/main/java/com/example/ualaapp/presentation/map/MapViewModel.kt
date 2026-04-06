package com.example.ualaapp.presentation.map

import androidx.lifecycle.ViewModel
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val baseRepository: BaseRepositoryImpl
) : ViewModel() {

}