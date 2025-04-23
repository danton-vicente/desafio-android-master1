package com.picpay.desafio.android.data_local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommentsConverter {
    @TypeConverter
    fun fromComments(optionValue: List<String>?): String? {
        if (optionValue == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {

        }.type
        return gson.toJson(optionValue, type)
    }

    @TypeConverter
    fun toComments(optionValueString: String?): List<String>? {
        if (optionValueString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {

        }.type
        return gson.fromJson(optionValueString, type)
    }
}
