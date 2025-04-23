package com.picpay.desafio.android.domain.model


data class CommentsData(
    val createdAt: String,
    val id: Int,
    val getUserId: Int,
    val text: String,
    val likes: Int,
    val isLiked: Boolean,
)
