package com.github.natalyamedvedeva.todoapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder

class TaskItemViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val taskNameTextView: TextView = itemView.findViewById(R.id.task_name_text_view)

    fun bind(task: Task) {
        taskNameTextView.text = String.format("%s - %s", task.name, task.priority.name)
    }
}