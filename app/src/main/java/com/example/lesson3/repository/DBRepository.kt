package com.example.lesson3.repository

import com.example.lesson3.API.PostId
import com.example.lesson3.data.Restaurant
import com.example.lesson3.data.Course
import com.example.lesson3.data.Food
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DBRepository {
    fun getFaculty(): Flow<List<Restaurant>>
    suspend fun insertFaculty(restaurant: Restaurant)
    suspend fun updateFaculty(restaurant: Restaurant)
    suspend fun insertAllFaculty(restaurantList: List<Restaurant>)
    suspend fun deleteFaculty(restaurant: Restaurant)
    suspend fun deleteAllFaculty()

    fun getAllGroups(): Flow<List<Course>>
    fun getFacultyGroups(restaurantId: UUID): Flow<List<Course>>
    suspend fun insertGroup(course: Course)
    suspend fun deleteGroup(course: Course)
    suspend fun deleteAllGroups()

    fun getAllStudents(): Flow<List<Food>>
    fun getGroupStudents(courseID: UUID): Flow<List<Food>>
    suspend fun insertStudent(food: Food)
    suspend fun deleteStudent(food: Food)
    suspend fun deleteAllStudents()
}
