package com.picpay.desafio.android.data_local.mappers

import com.picpay.desafio.android.data_local.model.CommentsEntity
import com.picpay.desafio.android.data_local.model.UserEntity
import com.picpay.desafio.android.domain.model.CommentsData
import com.picpay.desafio.android.domain.model.UserData

fun CommentsEntity.toDomain() = CommentsData(
    createdAt = this.createdAt,
    id = this.id,
    getUserId = this.userId,
    text = this.text,
    likes = this.likes,
    isLiked = this.isLiked
)

fun CommentsData.toEntity() = CommentsEntity(
    createdAt = this.createdAt,
    id = this.id,
    userId = this.getUserId,
    text = this.text,
    likes = this.likes,
    isLiked = this.isLiked
)
