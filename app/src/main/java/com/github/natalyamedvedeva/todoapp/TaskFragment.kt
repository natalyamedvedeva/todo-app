package com.github.natalyamedvedeva.todoapp

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskBinding
import java.text.SimpleDateFormat


class TaskFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskBinding

    lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_task, container, false
        )

        binding.nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        if (task.deadline != null) {
            val dateFormat = SimpleDateFormat.getDateInstance()
            binding.deadlineTextView.text = dateFormat.format(task.deadline!!.time)
        }
        binding.descriptionTextView.text = task.description
        binding.autoTransferTextView.text = task.autoReschedule.toString()
        return  binding.root
    }
}