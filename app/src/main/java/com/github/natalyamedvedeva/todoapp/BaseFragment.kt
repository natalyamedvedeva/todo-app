package com.github.natalyamedvedeva.todoapp

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.github.natalyamedvedeva.todoapp.data.Task

open class BaseFragment: Fragment() {

    interface OnNewTaskFragmentDataListener {
        fun onNewTaskCreated(task: Task)
    }

    interface OnTaskListFragmentDataListener {
        fun onTaskListAppeared(data: LiveData<List<Task>>)
    }
}