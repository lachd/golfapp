package com.example.golfapp.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.golfapp.database.entities.CourseHole

@Dao
interface CourseHoleDao {

    @Upsert
    suspend fun upsertCourseHole(courseHole: CourseHole)

    @Query("SELECT * FROM coursehole WHERE course_id = :courseId")
    suspend fun getHolesForCourse(courseId: Int): List<CourseHole>

    @Query("SELECT * FROM coursehole WHERE course_id = :courseId AND hole_number = :holeNumber")
    suspend fun getHoleFromCourseByNumber(courseId: Int, holeNumber: Int): CourseHole
}