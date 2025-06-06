package com.example.golfapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.golfapp.database.GolfDatabase
import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.daos.CourseHoleDao
import com.example.golfapp.database.daos.RoundDao
import com.example.golfapp.database.daos.RoundHoleDao
import com.example.golfapp.database.daos.SettingsDao
import com.example.golfapp.database.entities.Course
import com.example.golfapp.database.entities.CourseHole
import com.example.golfapp.database.entities.Round
import com.example.golfapp.database.entities.Setting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGolfDatabase(@ApplicationContext context: Context): GolfDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GolfDatabase::class.java,
            "golf_database"
        ).fallbackToDestructiveMigration(true)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = Room.databaseBuilder(
                            context,
                            GolfDatabase::class.java,
                            "golf_database"
                        ).build()
                        populateTestData(database)
                    }
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = Room.databaseBuilder(
                            context,
                            GolfDatabase::class.java,
                            "golf_database"
                        ).build()
                        clearTestRounds(database)
                    }
                }
            })
            .build()


    }


    private suspend fun populateTestData(database: GolfDatabase) {
        val courseDao = database.courseDao
        val courseHoleDao = database.courseHoleDao
        val settingDao = database.settingDao

        // Insert your test courses
        val moruya = Course(name = "Moruya")
        val narooma = Course(name = "Narooma")
        val mollymook = Course(name = "Mollymook")
        val catalina = Course(name = "Catalina")

        courseDao.upsertCourse(moruya)
        courseDao.upsertCourse(narooma)
        courseDao.upsertCourse(mollymook)
        courseDao.upsertCourse(catalina)

        // Add some holes for Moruya as an example
        val indexes = (1..18).shuffled()
        for (i in 1..18) {
            courseHoleDao.upsertCourseHole(
                CourseHole(
                    courseId = 1, // Assuming Moruya gets ID 1
                    holeNumber = i,
                    par = when (i % 6) { // Mix of par 3, 4, 5
                        0, 1 -> 4
                        2, 3 -> 3
                        else -> 5
                    },
                    index = indexes.get(i-1)
                )
            )
        }

        // Add a handicap
        settingDao.upsertSetting(setting = Setting(
            key = "handicap",
            value = "16"
        ))
    }

    private suspend fun clearTestRounds(database: GolfDatabase) {
        val roundDao = database.roundDao

        try {
            roundDao.getAllRounds().collect { rounds ->

                rounds.forEach { round ->
                    println(round)
                    roundDao.deleteRound(round)
                }
            }
        } catch (e: Exception) {
            println(e)
        }

    }

    @Provides
    fun provideRoundDao(database: GolfDatabase): RoundDao {
        return database.roundDao
    }

    @Provides
    fun provideCourseDao(database: GolfDatabase): CourseDao {
        return database.courseDao
    }

    @Provides
    fun provideCourseHoleDao(database: GolfDatabase): CourseHoleDao {
        return database.courseHoleDao
    }

    @Provides
    fun provideRoundHoleDao(database: GolfDatabase): RoundHoleDao {
        return database.roundHoleDao
    }

    @Provides
    fun provideSettingDao(database: GolfDatabase): SettingsDao {
        return database.settingDao
    }
}