package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import java.util.*

class TaskCategoryRepository private constructor(private val taskCategoryDao: TaskCategoryDao) {

    fun getTaskList(date: Date): LiveData<List<TaskWithCategories>> =taskCategoryDao.getTasksWithCategories(date)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TaskCategoryRepository? = null

        fun getInstance(taskCategoryDao: TaskCategoryDao) =
            instance ?: synchronized(this) {
                instance
                    ?: TaskCategoryRepository(taskCategoryDao).also { instance = it }
            }
    }
}