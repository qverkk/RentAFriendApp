package com.qverkk.touristrentafriend.data

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal): String {
        return bigDecimal.toString()
    }

    @TypeConverter
    fun toBigDecimal(str: String): BigDecimal {
        return BigDecimal(str)
    }
}