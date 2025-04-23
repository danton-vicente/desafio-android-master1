package com.picpay.desafio.android.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val img: String,
    val name: String,
    val username: String,
    val lastUpdated: Long,
    @TypeConverters(UserEntity::class)
    val comments: String,
)
