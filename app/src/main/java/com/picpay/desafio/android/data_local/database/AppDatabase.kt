package com.picpay.desafio.android.data_local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.picpay.desafio.android.data_local.dao.CommentsDAO
import com.picpay.desafio.android.data_local.dao.UserDAO
import com.picpay.desafio.android.data_local.model.CommentsEntity
import com.picpay.desafio.android.data_local.model.UserEntity


@Database(
    entities = [
        UserEntity::class,
        CommentsEntity::class
    ],
    version = 1, exportSchema = true
)

@TypeConverters(
    DateTypeConverter::class,
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDAO
    abstract fun getCommentsDao(): CommentsDAO
}
