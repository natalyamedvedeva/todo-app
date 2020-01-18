package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskBinding
import java.text.SimpleDateFormat

class TaskFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskBinding

    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task, container, false)
        task = arguments?.getSerializable("task") as Task
        setHasOptionsMenu(true)

        binding.nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        if (task.deadline != null) {
            val dateFormat = SimpleDateFormat.getDateInstance()
            binding.deadlineTextView.text = dateFormat.format(task.deadline!!.time)
        }
        binding.descriptionTextView.text = task.description
        binding.autoRescheduleTextView.text = task.autoReschedule.toString()
        return  binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_task) {
            val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(context!!).taskDao())
            taskRepository.delete(task)
            view?.findNavController()?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}