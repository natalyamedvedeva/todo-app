package com.github.natalyamedvedeva.todoapp.view

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.*

class TaskMenuController(val view: View, val task: Task) {

    private val context = view.context
    private val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(context).taskDao())

    fun done() {
        task.done = true
        taskRepository.insert(task)
    }

    fun edit() {
        view.findNavController().navigate(
            R.id.action_global_newTaskFragment,
            bundleOf(
                "title" to context.getString(R.string.edit_task_fragment_title),
                "task" to TaskWithCategories(task, mutableListOf())
            )
        )
    }

    fun delete() {
        taskRepository.delete(task)
    }
}