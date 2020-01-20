package com.github.natalyamedvedeva.todoapp.view.taskList

import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Task
import com.vanniktech.emoji.EmojiTextView

class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val taskItem: View = itemView.findViewById(R.id.task_item)
    private val priorityIconTextView: TextView = itemView.findViewById(R.id.priority_icon_text_view)
    private val taskNameTextView: TextView = itemView.findViewById(R.id.task_name_text_view)
    private val iconsTextView: EmojiTextView = itemView.findViewById(R.id.icons_text_view)

    fun bind(task: Task) {
        priorityIconTextView.text = "●"
        priorityIconTextView.setTextColor(ContextCompat.getColor(itemView.context, task.priority.color))

        taskNameTextView.text = task.name

        if (task.isDeadlineClose()) {
            iconsTextView.text = "🔥"
        } else {
            iconsTextView.text = ""
        }

        taskItem.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_global_taskFragment,
                bundleOf("task" to task)
            )
        )

        itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
            MenuInflater(v.context).inflate(R.menu.task_menu, menu)
        }
    }
}