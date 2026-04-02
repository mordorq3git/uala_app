package com.example.ualaapp.repository

import com.example.ualaapp.data.City

interface Repository {

    suspend fun getCities(): List<City>

}
