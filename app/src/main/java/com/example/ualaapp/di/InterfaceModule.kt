package com.example.ualaapp.di

import com.example.ualaapp.repository.Repository
import com.example.ualaapp.repository.implementations.BaseRepositoryImpl
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
}