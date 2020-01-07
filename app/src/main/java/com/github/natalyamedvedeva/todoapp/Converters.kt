package com.github.natalyamedvedeva.todoapp

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    private val datePattern = "dd/MM/yyyy"

    @TypeConverter
    fun stringToDate(value: String?): Date? {
        return value?.let { SimpleDateFormat(datePattern, Locale.US).parse(it) }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return date?.let { SimpleDateFormat(datePattern, Locale.US).format(it) }
    }

    @TypeConverter
    fun intToPriority(value: Int?): Priority? {
        return value?.let { Priority.values()[it] }
    }

    @TypeConverter
    fun priorityToInt(priority: Priority?): Int? {
        return priority?.ordinal
    }
}