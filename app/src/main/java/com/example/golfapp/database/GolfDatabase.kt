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
import com.example.golfapp.database.daos.RoundHoleDao
import com.example.golfapp.database.entities.Club
import com.example.golfapp.database.entities.HoleStat
import com.example.golfapp.database.entities.RoundHole
import com.example.golfapp.database.entities.Settings
import com.example.golfapp.database.entities.Shot
import com.example.golfapp.util.Converters

@Database(
    entities = [
        Course::class,
        CourseHole::class,
        Round::class,
        RoundHole::class,
        Club::class,
        Shot::class,
        HoleStat::class,
        Settings::class
    ],
    version = 3,
    exportSchema = false // this is to avoid schema export warnings during development
)
@TypeConverters(Converters::class)
abstract class GolfDatabase: RoomDatabase() {

    abstract val courseDao: CourseDao
    abstract val courseHoleDao: CourseHoleDao
    abstract val roundDao: RoundDao
    abstract val roundHoleDao: RoundHoleDao

    // TODO - delete this?
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