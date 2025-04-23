package com.picpay.desafio.android.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache")
data class CacheEntity(
    @PrimaryKey(autoGenerate = false)
    val path: String,
    val content: String,
    val data: String
)
