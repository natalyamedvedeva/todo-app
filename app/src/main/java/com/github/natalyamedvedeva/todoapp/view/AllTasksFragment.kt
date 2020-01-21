package com.github.natalyamedvedeva.todoapp.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.TaskCategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentAllTasksBinding
import com.github.natalyamedvedeva.todoapp.view.taskList.TaskListFragment

/**
 * A simple [Fragment] subclass.
 */
class AllTasksFragment : Fragment() {

    private lateinit var binding: FragmentAllTasksBinding
    private val child = TaskListFragment()
    private lateinit var taskCategoryRepository: TaskCategoryRepository
    private var ascOrder = false
    private var query: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskCategoryRepository = TaskCategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).taskCategoryDao())
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_all_tasks, container, false
        )
        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                query = newText ?: ""
                findByName()
                return true
            }
        })
        binding.sort.setOnClickListener {
            ascOrder = !ascOrder
            findByName()
            (it as ImageView).setImageResource(if (ascOrder) R.drawable.ic_arrow_downward_black_24dp else R.drawable.ic_arrow_upward_black_24dp)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.child_content_container, child)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        findByName()
    }


    private fun findByName() {
        taskCategoryRepository.getTasksByName(query).observe(this, Observer {
            child.onTaskListAppeared(it.toMutableList().also {
                if (ascOrder) it.sortBy { it.priority } else it.sortByDescending { it.priority }
            })
        })
    }
}
