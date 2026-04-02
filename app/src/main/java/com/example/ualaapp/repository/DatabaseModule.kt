package com.example.ualaapp.repository

import android.content.Context
import androidx.room.Room
import com.example.ualaapp.repository.implementations.database.AppDatabase
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideCityDao(db: AppDatabase): CityDao {
        return db.cityDao()
    }

    @Provides
    fun provideFavouriteDao(db: AppDatabase): FavouriteDao {
        return db.favouriteDao()
    }
}