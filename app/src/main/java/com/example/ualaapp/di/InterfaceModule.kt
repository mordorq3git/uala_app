package com.example.ualaapp.di

import com.example.ualaapp.repository.BaseRepository
import com.example.ualaapp.repository.implementations.ApiRepositoryImpl
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.repository.implementations.DataBaseRepositoryImpl
import com.example.ualaapp.repository.implementations.api.ApiRepository
import com.example.ualaapp.repository.implementations.database.DatabaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    @Singleton
    abstract fun bindBaseRepository(
        baseRepositoryImpl: BaseRepositoryImpl
    ): BaseRepository

    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(
        baseRepositoryImpl: DataBaseRepositoryImpl
    ): DatabaseRepository

    @Binds
    @Singleton
    abstract fun bindApiRepository(
        apiRepositoryImpl: ApiRepositoryImpl
    ): ApiRepository
}