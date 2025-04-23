package com.picpay.desafio.android.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val img: String,
    val name: String,
    val username: String,
    val bitcoinWallet: String,
    val ethereumWallet: String,
    val litecoinWallet: String,
    val lastUpdated: Long,
)
