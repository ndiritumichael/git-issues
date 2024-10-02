package com.devmike.database.convertors

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = value.let { Json.encodeToString(it) }

    @TypeConverter
    fun toStringList(value: String): List<String> =
        value.let { Json.decodeFromString<List<String>>(it) }
}
