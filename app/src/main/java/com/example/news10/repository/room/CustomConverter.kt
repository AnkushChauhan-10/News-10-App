package com.example.news10.repository.room

import androidx.room.TypeConverter
import java.util.*

class CustomConverter {

    @TypeConverter
    fun dateToLong(date: Date):Long{
        return date.time
    }

}