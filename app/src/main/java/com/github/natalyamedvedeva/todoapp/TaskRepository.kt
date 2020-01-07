package com.github.natalyamedvedeva.todoapp

import java.util.*

class TaskRepository private constructor(private val taskDao: TaskDao) {

    fun insert(task: Task) = taskDao.insert(task)

    fun getTaskList(date: Date) = taskDao.getTaskList(date)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) =
            instance ?: synchronized(this) {
                instance ?: TaskRepository(taskDao).also { instance = it }
            }
    }
}