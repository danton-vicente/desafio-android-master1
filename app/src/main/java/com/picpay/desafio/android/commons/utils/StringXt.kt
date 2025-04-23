package com.picpay.desafio.android.commons.utils

fun String.getInitials(): String {
    val parts = this.split(" ")
    val firstNameInitial = parts.getOrNull(0)?.firstOrNull()?.toString() ?: ""
    val lastNameInitial = parts.getOrNull(parts.size - 1)?.firstOrNull()?.toString() ?: ""

    return (firstNameInitial + lastNameInitial).uppercase()
}
