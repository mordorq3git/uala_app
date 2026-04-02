package com.example.ualaapp.presentation.citieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.ualaapp.repository.CitiesApiService
import com.example.ualaapp.repository.CityPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val apiService: CitiesApiService
) : ViewModel() {

    val citiesFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CityPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}