package com.github.natalyamedvedeva.todoapp.utils

import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import java.util.*

fun rescheduleTasks(context: ComponentActivity) {
    val today = Calendar.getInstance()
    today[Calendar.HOUR_OF_DAY] = 0
    today[Calendar.MINUTE] = 0
    today[Calendar.SECOND] = 0

    val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(context).taskDao())
    val tasksLiveData = taskRepository.getReschedulingTaskList()
    tasksLiveData.observe(context, Observer { tasks ->
        val fTasks = tasks?.filter { it.date < today.time
                && (it.deadline == null || it.deadline!! > it.date)}

        fTasks?.forEach {
            if (it.deadline != null && it.deadline!! < today.time) {
                it.date = it.deadline!!
            } else {
                it.date = today.time
            }
            taskRepository.update(it)
        }
        tasksLiveData.removeObservers(context)
    })
}