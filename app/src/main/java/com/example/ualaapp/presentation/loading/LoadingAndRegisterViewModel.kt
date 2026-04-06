package com.example.ualaapp.presentation.loading

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualaapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingAndRegisterViewModel @Inject constructor(
    private val baseRepository: Repository
) : ViewModel() {
    private val _loadingUiState = MutableStateFlow<LoadingUIState>(LoadingUIState.Idle)
    val loadingUIState: StateFlow<LoadingUIState> = _loadingUiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            Log.d("TAG", "loadData: comienzo")

            _loadingUiState.update { LoadingUIState.Loading }

            baseRepository.getCities()

            _loadingUiState.update { LoadingUIState.Success }

            Log.d("TAG", "loadData: fin")
        }
    }
}