package com.github.natalyamedvedeva.todoapp.view.taskList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Task

class TaskItemAdapter : RecyclerView.Adapter<TaskItemViewHolder>() {

    private var taskList = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_view, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount() = taskList.size

    fun addItem(Task: Task) {
        taskList.add(Task)
        notifyItemInserted(taskList.size - 1)
    }

    fun removeItem(position: Int) {
        taskList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun resetItems(tasks: List<Task>) {
        val diffCallback: DiffUtil.Callback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = this@TaskItemAdapter.taskList.size

            override fun getNewListSize(): Int = tasks.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                this@TaskItemAdapter.taskList[oldItemPosition].id == tasks[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                this@TaskItemAdapter.taskList[oldItemPosition] == tasks[newItemPosition]
        }
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.taskList.clear()
        this.taskList.addAll(tasks)
        diffResult.dispatchUpdatesTo(this)
    }
}