package com.picpay.desafio.android.data_remote.mappers

import com.picpay.desafio.android.data_remote.model.CommentsResponse
import com.picpay.desafio.android.domain.model.CommentsData

fun CommentsResponse.toDomain() = CommentsData(
    createdAt = this.createdAt ?: "",
    id = this.id ?: -1,
    getUserId = this.getUserId ?: -1,
    text = this.text ?: "",
    likes = this.likes ?: 0,
    isLiked = this.isLiked == true
)

fun CommentsData.toResponse() = CommentsResponse(
    createdAt = this.createdAt,
    id = this.id,
    getUserId = this.getUserId,
    text = this.text,
    likes = this.likes,
    isLiked = this.isLiked
)
