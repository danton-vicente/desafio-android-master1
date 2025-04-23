package com.picpay.desafio.android.data_local.mappers

import com.picpay.desafio.android.data_local.model.UserEntity
import com.picpay.desafio.android.domain.model.UserData

fun UserEntity.toDomain() = UserData(
    id = this.id,
    name = this.name,
    username = this.username,
    img = this.img,
    bitcoinWallet = this.bitcoinWallet,
    ethereumWallet = this.ethereumWallet,
    litecoinWallet = this.litecoinWallet,
)

fun UserData.toEntity(currentTime: Long) = UserEntity(
    id = this.id,
    name = this.name,
    username = this.username,
    img = this.img,
    bitcoinWallet = this.bitcoinWallet,
    ethereumWallet = this.ethereumWallet,
    litecoinWallet = this.litecoinWallet,
    lastUpdated = currentTime,
)
