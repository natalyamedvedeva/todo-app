package com.github.natalyamedvedeva.todoapp.view

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.*

class TaskMenuController(val view: View, val task: Task) {

    private val context = view.context

    fun editTask() {
        view.findNavController().navigate(
            R.id.action_global_newTaskFragment,
            bundleOf(
                "title" to context.getString(R.string.edit_task_fragment_title),
                "task" to TaskWithCategories(task, mutableListOf())
            )
        )
    }

    fun deleteTask() {
        val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(context).taskDao())
        taskRepository.delete(task)
    }
}