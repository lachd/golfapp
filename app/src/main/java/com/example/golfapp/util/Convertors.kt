package com.example.golfapp.util

import androidx.room.TypeConverter
import com.example.golfapp.util.Stat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromWeather(weather: Weather): String = weather.name

    @TypeConverter
    fun toWeather(weather: String): Weather = Weather.valueOf(weather)

    @TypeConverter
    fun fromWind(wind: Wind): String = wind.name

    @TypeConverter
    fun toWind(wind: String): Wind = Wind.valueOf(wind)

    @TypeConverter
    fun fromStat(stat: Stat): String = stat.name

    @TypeConverter
    fun toStat(stat: String): Stat = Stat.valueOf(stat)

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.format(formatter)

    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? = date?.let {
        LocalDate.parse(it, formatter)
    }
}
