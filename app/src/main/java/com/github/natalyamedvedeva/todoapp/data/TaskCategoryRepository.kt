package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import java.util.*

class TaskCategoryRepository private constructor(private val taskCategoryDao: TaskCategoryDao) {

    fun getTaskList(date: Date): LiveData<List<TaskWithCategories>> = taskCategoryDao.getTasksWithCategories(date)

    fun insertTask(task: TaskWithCategories) {
        Thread {
            val taskId = taskCategoryDao.insertTask(task.task)
            taskCategoryDao.deleteByTask(taskId)
            task.categories.forEach {
                taskCategoryDao.insertTaskCategory(TaskCategoryCrossRef(taskId, it.id))
            }
        }.start()
    }
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