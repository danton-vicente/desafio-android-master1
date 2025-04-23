package com.picpay.desafio.android.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data_local.model.CacheEntity
import com.picpay.desafio.android.data_local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT MAX(lastUpdated) FROM user")
    fun getMostRecentUpdate(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplaceUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: Int): UserEntity?

    @Query("SELECT * FROM user LIMIT :limit OFFSET :offset")
    fun getPaginatedUsers(limit: Int, offset: Int): List<UserEntity>

    @Query("DELETE FROM user")
    fun deleteAllUsers()

}
