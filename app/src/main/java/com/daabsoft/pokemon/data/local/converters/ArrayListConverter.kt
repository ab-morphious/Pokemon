package com.daabsoft.pokemon.data.local.converters

import androidx.room.TypeConverter
import com.daabsoft.pokemon.data.local.entity.StatElement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ArrayListConverter {

    @TypeConverter
    fun fromStringArrayList(value: List<StatElement>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<StatElement> {
        return try {
            val gson = Gson()
            val type: Type = object : TypeToken<List<StatElement>>() {}.getType()
            val stats: List<StatElement> = gson.fromJson(value, type)
            return stats
        } catch (ignore: Exception) { arrayListOf<StatElement>() }
    }
}
