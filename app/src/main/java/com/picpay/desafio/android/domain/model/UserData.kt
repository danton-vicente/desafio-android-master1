package com.picpay.desafio.android.domain.model

data class UserData(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val img: String = "",
    val bitcoinWallet: String = "",
    val ethereumWallet: String = "",
    val litecoinWallet: String = "",
)
