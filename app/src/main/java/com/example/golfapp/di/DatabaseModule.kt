package com.example.golfapp.di

import android.content.Context
import androidx.room.Room
import com.example.golfapp.database.GolfDatabase
import com.example.golfapp.database.daos.CourseDao
import com.example.golfapp.database.daos.CourseHoleDao
import com.example.golfapp.database.daos.RoundDao
import com.example.golfapp.database.entities.Round
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGolfDatbase(@ApplicationContext context: Context): GolfDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GolfDatabase::class.java,
            "golf_database"
        ).fallbackToDestructiveMigration(true)
            .build()
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
}