package com.github.natalyamedvedeva.todoapp.view.taskList

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.view.BaseFragment
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskWithCategories
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskListBinding

class TaskListFragment : BaseFragment(),
    BaseFragment.OnTaskListFragmentDataListener {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var taskItemAdapter: TaskItemAdapter
    private var taskList: LiveData<List<TaskWithCategories>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task_list, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.task_menu, menu)
    }

    private fun initRecyclerView() {
        tasksRecyclerView = binding.tasksRecyclerView
        tasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        taskItemAdapter = TaskItemAdapter()
        tasksRecyclerView.adapter = taskItemAdapter
        updateTaskList()
    }

    private fun updateTaskList() {
        taskList?.let { taskItemAdapter.resetItems(it) }
    }

    override fun onTaskListAppeared(data: List<TaskWithCategories>) {
        taskList = data
        updateTaskList()
    }
}
