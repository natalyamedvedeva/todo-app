package com.github.natalyamedvedeva.todoapp

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.data.Task

class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val taskItem: View = itemView.findViewById(R.id.task_item)
    private val taskNameTextView: TextView = itemView.findViewById(R.id.task_name_text_view)

    fun bind(task: Task) {
        taskNameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        taskItem.setOnClickListener {
            val context = itemView.context
            //TODO navigate DayTaskListFragment to TaskFragment with task
        }
    }
}