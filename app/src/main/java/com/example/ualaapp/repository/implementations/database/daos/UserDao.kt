package com.example.ualaapp.repository.implementations.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ualaapp.repository.implementations.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM users WHERE id_user = :id_user")
    suspend fun getUserEntity(id_user: Long): UserEntity

    @Query("SELECT id_user FROM users WHERE name = :name")
    suspend fun getUserId(name: String): Long

}