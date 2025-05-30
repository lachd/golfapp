package com.example.golfapp.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromWeather(weather: Weather): String = weather.name

    @TypeConverter
    fun toWeather(weather: String): Weather = Weather.valueOf(weather)

    @TypeConverter
    fun fromWind(wind: Wind): String = wind.name

    @TypeConverter
    fun toWind(wind: String): Wind = Wind.valueOf(wind)

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.format(formatter)

    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? = date?.let {
        LocalDate.parse(it, formatter)
    }
}
