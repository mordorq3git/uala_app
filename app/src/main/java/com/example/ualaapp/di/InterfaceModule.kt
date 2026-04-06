package com.example.ualaapp.di

import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
import com.example.ualaapp.repository.implementations.DataBaseRepositoryImpl
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
    ): Repository

    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(
        baseRepositoryImpl: DataBaseRepositoryImpl
    ): DatabaseRepository
}