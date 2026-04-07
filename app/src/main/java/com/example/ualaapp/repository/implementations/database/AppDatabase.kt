package com.example.ualaapp.repository.implementations.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ualaapp.repository.implementations.database.daos.CityDao
import com.example.ualaapp.repository.implementations.database.daos.FavouriteDao
import com.example.ualaapp.repository.implementations.database.daos.UserDao
import com.example.ualaapp.repository.implementations.database.entities.CityEntity
import com.example.ualaapp.repository.implementations.database.entities.FavouriteEntity
import com.example.ualaapp.repository.implementations.database.entities.UserEntity

@Database(entities = [UserEntity::class, CityEntity::class, FavouriteEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun userDao(): UserDao

    abstract fun favouriteDao(): FavouriteDao
}