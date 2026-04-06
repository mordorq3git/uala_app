package com.example.ualaapp.presentation.loading

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
    private val _loadingAndRegistryUiState =
        MutableStateFlow<LoadingAndRegistryUIState>(LoadingAndRegistryUIState.Idle)
    val loadingAndRegistryUIState: StateFlow<LoadingAndRegistryUIState> =
        _loadingAndRegistryUiState.asStateFlow()

    private val _registerUserValue = MutableStateFlow("")
    val registerUserValue: StateFlow<String> = _registerUserValue.asStateFlow()

    fun onEvent(event: LoadingIntent) {
        when(event) {
            LoadingIntent.Load -> loadData()
        }
    }

    fun onEvent(event: RegistryIntent) {
        when(event) {
            is RegistryIntent.SetUserName -> _registerUserValue.update { event.username }
            RegistryIntent.Register -> {
                TODO()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _loadingAndRegistryUiState.update { LoadingAndRegistryUIState.Loading }

            baseRepository.getCities()

            _loadingAndRegistryUiState.update { LoadingAndRegistryUIState.Success }
        }
    }
}