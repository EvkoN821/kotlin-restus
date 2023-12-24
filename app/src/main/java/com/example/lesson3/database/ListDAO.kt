package com.example.lesson3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lesson3.data.Restaurant
import com.example.lesson3.data.Course
import com.example.lesson3.data.Food
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ListDAO {
        @Query("SELECT * FROM restaurants order by name")
        fun getFaculty(): Flow<List<Restaurant>>

        @Insert(entity = Restaurant::class, onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertFaculty(restaurant: Restaurant)

        @Update(entity = Restaurant::class)
        suspend fun updateFaculty(restaurant: Restaurant)

        @Insert(entity = Restaurant::class, onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAllFaculty(restaurantList: List<Restaurant>)

        @Delete(entity = Restaurant::class)
        suspend fun deleteFaculty(restaurant: Restaurant)

        @Query("DELETE FROM restaurants")
        suspend fun deleteAllFaculty()

        @Query("SELECT * FROM courses")
        fun getAllGroups(): Flow<List<Course>>

        @Query("SELECT * FROM courses where restaurant_id=:restaurantId")
        fun getFacultyGroups(restaurantId : UUID): Flow<List<Course>>

        @Insert(entity = Course::class, onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertGroup(course: Course)

        @Delete(entity = Course::class)
        suspend fun deleteGroup(course: Course)

        @Query("DELETE FROM courses")
        suspend fun deleteAllGroups()

        @Query("SELECT * FROM foods")
        fun getAllStudents(): Flow<List<Food>>

        @Query("SELECT * FROM foods where course_id=:courseId")
        fun getGroupStudents(courseId : UUID): Flow<List<Food>>

        @Insert(entity = Food::class, onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertStudent(food: Food)

        @Delete(entity = Food::class)
        suspend fun deleteStudent(food: Food)

        @Query("DELETE FROM foods")
        suspend fun deleteAllStudents()
}
