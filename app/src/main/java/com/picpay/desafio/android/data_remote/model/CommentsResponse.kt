package com.picpay.desafio.android.data_remote.model

import com.google.gson.annotations.SerializedName


data class CommentsResponse(
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("getUserId") val getUserId: Int?,
    @SerializedName("text") val text: String?,
    @SerializedName("likes") val likes: Int?,
    @SerializedName("isLiked") val isLiked: Boolean?,
)
