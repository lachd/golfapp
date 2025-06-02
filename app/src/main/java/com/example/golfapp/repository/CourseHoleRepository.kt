package com.example.golfapp.repository

import com.example.golfapp.database.daos.CourseHoleDao
import com.example.golfapp.database.entities.CourseHole
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseHoleRepository @Inject constructor(
    private val courseHoleDao: CourseHoleDao
){

    suspend fun getAllCourseHolesForCourse(courseId: Int): List<CourseHole>? = courseHoleDao.getHolesForCourse(courseId)

    suspend fun getCourseHoleFromCourseByNumber(courseId: Int, holeNumber: Int): CourseHole?
        = courseHoleDao.getHoleFromCourseByNumber(courseId, holeNumber)
}