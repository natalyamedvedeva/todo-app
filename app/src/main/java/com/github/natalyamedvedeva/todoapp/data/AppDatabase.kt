package com.github.natalyamedvedeva.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class, Category::class, TaskCategoryCrossRef::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun taskCategoryDao(): TaskCategoryDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "todo_database8.db").build()
    }
}