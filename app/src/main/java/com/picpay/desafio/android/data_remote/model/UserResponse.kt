package com.picpay.desafio.android.data_remote.model

import com.google.gson.annotations.SerializedName


data class UserResponse(
    @SerializedName("img") val img: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("bitcoinWallet") val bitcoinWallet: String?,
    @SerializedName("ethereumWallet") val ethereumWallet: String?,
    @SerializedName("litecoinWallet") val litecoinWallet: String?,
)
