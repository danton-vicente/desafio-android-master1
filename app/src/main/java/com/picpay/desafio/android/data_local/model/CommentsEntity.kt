package com.picpay.desafio.android.data_local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comments")
data class CommentsEntity(
    val createdAt: String,
    @PrimaryKey(autoGenerate = false) val id: Int,
    val userId: Int,
    val text: String,
    val likes: Int,
    val isLiked: Boolean,
)
