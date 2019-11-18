package com.github.natalyamedvedeva.todoapp

import java.util.*
import kotlin.collections.HashMap

class TaskManager {
    private var tasks: HashMap<Date, MutableList<Task>> = HashMap()

    fun addTask(date: Date, task: Task) {
        if (tasks[date] == null) {
            tasks[date] = mutableListOf()
        }
        tasks[date]?.add(task)
    }

    fun getTaskList(date: Date) : List<Task> {
        return tasks[date]?.toList() ?: emptyList()
    }
}