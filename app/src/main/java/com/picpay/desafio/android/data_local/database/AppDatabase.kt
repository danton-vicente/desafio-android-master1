package com.picpay.desafio.android.data_local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.picpay.desafio.android.data_local.dao.UserDAO
import com.picpay.desafio.android.data_local.model.UserEntity


@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1, exportSchema = true
)

@TypeConverters(
    DateTypeConverter::class,
    CommentsConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDAO
}
