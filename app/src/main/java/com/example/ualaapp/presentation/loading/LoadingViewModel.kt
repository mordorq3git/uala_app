package com.example.ualaapp.presentation.loading

import androidx.lifecycle.ViewModel
import com.example.ualaapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val baseRepository: Repository
) : ViewModel()