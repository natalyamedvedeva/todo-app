package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.TaskCategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentAllTasksBinding
import com.github.natalyamedvedeva.todoapp.view.taskList.TaskListFragment

class AllTasksFragment : Fragment() {

    private lateinit var binding: FragmentAllTasksBinding
    private val child = TaskListFragment()
    private var ascOrder = false
    private var query: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_all_tasks, container, false
        )
        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                view?.clearFocus()
                return true
            }

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

    override fun onDestroyView() {
        super.onDestroyView()
        view?.clearFocus()
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
        val taskCategoryRepository = TaskCategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).taskCategoryDao())
        taskCategoryRepository.getTasksByName(query).observe(this, Observer { list ->
            child.onTaskListAppeared(list.toMutableList().also { mList ->
                if (ascOrder) mList.sortBy { it.priority } else mList.sortByDescending { it.priority }
            })
        })
    }
}
