package com.example.lesson3.repository

import com.example.lesson3.data.Restaurant
import com.example.lesson3.data.Course
import com.example.lesson3.data.Food
import com.example.lesson3.database.ListDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class OfflineDBRepository(val dao: ListDAO): DBRepository {
    override fun getFaculty(): Flow<List<Restaurant>> =dao.getFaculty()
    override suspend fun insertFaculty(restaurant: Restaurant) = dao.insertFaculty(restaurant)
    override suspend fun updateFaculty(restaurant: Restaurant) =dao.updateFaculty(restaurant)
    override suspend fun insertAllFaculty(restaurantList: List<Restaurant>) =dao.insertAllFaculty(restaurantList)
    override suspend fun deleteFaculty(restaurant: Restaurant) =dao.deleteFaculty(restaurant)
    override suspend fun deleteAllFaculty() =dao.deleteAllFaculty()

    override fun getAllGroups(): Flow<List<Course>> =dao.getAllGroups()
    override fun getFacultyGroups(restaurantId : UUID): Flow<List<Course>> =dao.getFacultyGroups(restaurantId)
    override suspend fun insertGroup(course: Course) =dao.insertGroup(course)
    override suspend fun deleteGroup(course: Course) =dao.deleteGroup(course)
    override suspend fun deleteAllGroups() =dao.deleteAllGroups()

    override fun getAllStudents(): Flow<List<Food>> =dao.getAllStudents()
    override fun getGroupStudents(courseId : UUID): Flow<List<Food>> =dao.getGroupStudents(courseId)
    override suspend fun insertStudent(food: Food) =dao.insertStudent(food)
    override suspend fun deleteStudent(food: Food) =dao.deleteStudent(food)
    override suspend fun deleteAllStudents() =dao.deleteAllStudents()
}