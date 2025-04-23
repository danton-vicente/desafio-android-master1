package com.picpay.desafio.android.data_remote.mappers

import com.picpay.desafio.android.data_remote.model.UserResponse
import com.picpay.desafio.android.domain.model.UserData

fun UserResponse.toDomain() = UserData(
    id = id,
    name = name,
    username = username,
    img = img,
    comments = comments ?: emptyList(),
)
