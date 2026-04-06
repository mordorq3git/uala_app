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

private const val UserNameMinLength = 3

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
    private val _registerButtonEnabled = MutableStateFlow(false)
    val registerButtonEnabled: StateFlow<Boolean> = _registerButtonEnabled.asStateFlow()

    fun onEvent(event: LoadingIntent) {
        when(event) {
            LoadingIntent.Load -> loadData()
        }
    }

    fun onEvent(event: RegistryIntent) {
        when(event) {
            is RegistryIntent.SetUserName -> {
                userNameValidator(event.username)
            }
            RegistryIntent.Register -> {
                TODO()
            }
        }
    }

    private fun userNameValidator(username: String) {
        _registerUserValue.update { username }

        if(username.length > UserNameMinLength) {
            _registerButtonEnabled.update { true }
        } else {
            _registerButtonEnabled.update { false }
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