package com.example.ualaapp.repository

import android.content.Context
import androidx.room.Room
import com.example.ualaapp.di.DatabaseModule
import com.example.ualaapp.repository.implementations.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideInMemoryDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideCityDao(db: AppDatabase) = db.cityDao()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideFavouriteDao(db: AppDatabase) = db.favouriteDao()

}