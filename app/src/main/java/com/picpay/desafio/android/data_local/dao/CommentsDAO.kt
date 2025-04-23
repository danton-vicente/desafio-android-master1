package com.picpay.desafio.android.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data_local.model.CommentsEntity
import com.picpay.desafio.android.data_local.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<CommentsEntity>)

    @Query("SELECT * FROM comments WHERE userId = :userId")
    fun getAllCommentsFromUserId(userId: Int): List<CommentsEntity>

}
