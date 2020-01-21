package com.github.natalyamedvedeva.todoapp.data

import java.util.*

class TaskRepository private constructor(private val taskDao: TaskDao) {

    fun insert(task: Task) {
        Thread {
            taskDao.insert(task)
        }.start()
    }

    fun update(task: Task) {
        Thread {
            taskDao.update(task)
        }.start()
    }

    fun delete(task: Task) {
        Thread {
            taskDao.delete(task)
        }.start()
    }

    fun getTaskList(date: Date) = taskDao.getTaskList(date)

    fun getReschedulingTaskList() = taskDao.getReschedulingTaskList()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) =
            instance ?: synchronized(this) {
                instance
                    ?: TaskRepository(taskDao).also { instance = it }
            }
    }
}