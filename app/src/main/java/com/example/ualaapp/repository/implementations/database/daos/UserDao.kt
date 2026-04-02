package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ualaapp.repository.implementations.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE name = :name")
    fun getUserEntity(name: String): UserEntity

    @Query("SELECT id_user FROM users WHERE name = :name")
    fun getUserId(name: String): Int

}