package com.picpay.desafio.android.commons.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> toJson(obj: T): String {
    return Gson().toJson(obj)
}

inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson(json, object : TypeToken<T>() {}.type)
}
