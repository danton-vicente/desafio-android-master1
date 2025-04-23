package com.picpay.desafio.android.domain.model

data class UserData(
    val id: Int,
    val name: String,
    val username: String,
    val img: String,
    val comments: List<String>
)
