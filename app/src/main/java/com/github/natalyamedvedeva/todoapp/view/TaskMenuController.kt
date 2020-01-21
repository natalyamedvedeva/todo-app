package com.github.natalyamedvedeva.todoapp.view

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.*

class TaskMenuController(val view: View, val task: TaskWithCategories) {

    private val context = view.context
    private val taskCategoryRepository = TaskCategoryRepository.getInstance(AppDatabase.getInstance(context).taskCategoryDao())

    fun done() {
        task.done = true
        task.autoReschedule = false
        taskCategoryRepository.insertTask(task)
    }

    fun edit() {
        view.findNavController().navigate(
            R.id.action_global_newTaskFragment,
            bundleOf(
                "title" to context.getString(R.string.edit_task_fragment_title),
                "task" to task
            )
        )
    }

    fun delete() {
        TaskRepository.getInstance(AppDatabase.getInstance(context).taskDao()).delete(task.task)
    }
}