package com.github.natalyamedvedeva.todoapp

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val taskNameTextView: TextView = itemView.findViewById(R.id.task_name_text_view)

    fun bind(task: Task) {
        taskNameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        taskNameTextView.setOnClickListener {
            val context = itemView.context
            val taskActivityIntent = Intent(context, TaskActivity::class.java)
            taskActivityIntent.putExtra("task", task)
            context.startActivity(taskActivityIntent)
        }
    }
}