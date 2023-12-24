package com.example.lesson3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lesson3.data.Restaurant
import com.example.lesson3.data.Course
import com.example.lesson3.data.Food

@Database(
    entities = [Restaurant::class,
                Course::class,
                Food::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ListTypeConverters::class)

abstract class ListDatabase: RoomDatabase() {
    abstract fun listDAO(): ListDAO

    companion object{
        @Volatile
        private var INSTANCE: ListDatabase? = null

        fun getDatabase(context: Context): ListDatabase {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also{ INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context,
                ListDatabase::class.java,
                "list_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}





