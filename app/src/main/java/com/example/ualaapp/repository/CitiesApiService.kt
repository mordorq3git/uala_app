package com.example.ualaapp.repository

import com.example.ualaapp.data.City
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApiService {

    @GET("https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json")
    suspend fun getPagedCities(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<City>

    @GET("https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json")
    suspend fun getPagedCities(): List<City>

}