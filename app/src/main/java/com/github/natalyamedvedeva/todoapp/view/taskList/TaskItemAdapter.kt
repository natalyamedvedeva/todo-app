package com.github.natalyamedvedeva.todoapp.view.taskList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Task

class TaskItemAdapter : RecyclerView.Adapter<TaskItemViewHolder>() {

    private var taskList = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_view, parent, false)
        return TaskItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount() = taskList.size

    fun addItems(tasks: Collection<Task>) {
        this.taskList.addAll(tasks)
        notifyDataSetChanged()
    }

    fun clearItems() {
        taskList.clear()
        notifyDataSetChanged()
    }
}