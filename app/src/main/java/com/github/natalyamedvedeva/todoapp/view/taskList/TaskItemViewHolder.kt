package com.github.natalyamedvedeva.todoapp.view.taskList

import android.graphics.Paint
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.TaskWithCategories
import com.github.natalyamedvedeva.todoapp.view.TaskMenuController

class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val taskItem: View = itemView.findViewById(R.id.task_item)
    private val priorityIconTextView: TextView = itemView.findViewById(R.id.priority_icon_text_view)
    private val taskNameTextView: TextView = itemView.findViewById(R.id.task_name_text_view)
    private val iconsTextView: TextView = itemView.findViewById(R.id.icons_text_view)

    fun bind(task: TaskWithCategories) {
        priorityIconTextView.text = "●"
        priorityIconTextView.setTextColor(ContextCompat.getColor(itemView.context, task.priority.color))

        iconsTextView.text = ""

        taskNameTextView.text = task.name
        if (task.done) {
            taskNameTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            taskNameTextView.paintFlags = Paint.LINEAR_TEXT_FLAG
            if (task.task.isDeadlineClose()) {
                iconsTextView.text = "🔥"
            }
        }

        taskItem.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_global_taskFragment,
                bundleOf("task" to task)
            )
        )

        itemView.setOnCreateContextMenuListener { menu, v, _ ->
            MenuInflater(v.context).inflate(R.menu.task_menu, menu)
            val menuController = TaskMenuController(v, task.task)
            menu.findItem(R.id.done_task).isVisible = !task.done
            menu.findItem(R.id.done_task).setOnMenuItemClickListener {
                menuController.done()
                true
            }
            menu.findItem(R.id.edit_task).setOnMenuItemClickListener {
                menuController.edit()
                true
            }
            menu.findItem(R.id.delete_task).setOnMenuItemClickListener {
                menuController.delete()
                true
            }
        }
    }
}