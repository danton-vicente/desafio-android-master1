package com.picpay.desafio.android.data_remote.mappers

import com.picpay.desafio.android.data_remote.model.UserResponse
import com.picpay.desafio.android.domain.model.UserData

fun UserResponse.toDomain() = UserData(
    id = this.id ?: -1,
    name = this.name ?: "",
    username = this.username ?: "",
    img = this.img ?: "",
    bitcoinWallet = this.bitcoinWallet ?: "",
    ethereumWallet = this.ethereumWallet ?: "",
    litecoinWallet = this.litecoinWallet ?: ""
)
