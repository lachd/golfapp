package com.example.golfapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.golfapp.database.entities.Course
import com.example.golfapp.database.entities.CourseHole
import com.example.golfapp.database.entities.Round
import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.daos.CourseHoleDao
import com.example.golfapp.database.daos.RoundDao
import com.example.golfapp.util.Converters

@Database(
    entities = [
        Course::class,
        CourseHole::class,
        Round::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class GolfDatabase: RoomDatabase() {

    abstract val courseDao: CourseDao
    abstract val courseHoleDao: CourseHoleDao
    abstract val roundDao: RoundDao

    companion object {
        @Volatile
        private var INSTANCE: GolfDatabase? = null

        fun getDatabase(context: Context): GolfDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GolfDatabase::class.java,
                    "golf_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}