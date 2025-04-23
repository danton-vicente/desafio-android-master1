package com.picpay.desafio.android.data_local.database

import androidx.room.TypeConverter
import java.sql.Date

class DateTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): java.util.Date? {
        return if (timestamp == null) null else java.util.Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: java.util.Date?): Long? {
        return date?.time
    }
}
