package com.example.golfapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.golfapp.database.entities.Course

@Dao
interface CourseDao {

    @Upsert
    suspend fun upsertCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Query("SELECT * FROM course")
    fun getCourses(): List<Course>

    @Query("SELECT * FROM course where course.name = :name")
    suspend fun getCourseByName(name: String): Course

    @Query("SELECT * FROM course where course.id = :id")
    suspend fun getCourseById(id: Int): Course

//    @Query("SELECT course.name, coursehole.hole_number, coursehole.par, coursehole.`index` FROM course join coursehole on coursehole.course_id = course.id WHERE name = :name")
//    fun getCourseByName(name: String): CourseWithHoles

//    @Query("SELECT * FROM course JOIN courseHole ON courseHole.course_id = course.id")
//    fun getCoursesWithHoles(): Flow<List<Course>>

//    data class CourseWithHoles(
//        val name: String,
//        @ColumnInfo(name = "hole_number") val holeNumber: Int,
//        val par: Int,
//        val index: Int,
//    )
}