package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.*
import com.github.natalyamedvedeva.todoapp.databinding.FragmentDayTaskListBinding
import com.github.natalyamedvedeva.todoapp.view.taskList.TaskListFragment
import java.text.SimpleDateFormat
import java.util.*

class DayTaskListFragment : BaseFragment() {

    private lateinit var binding: FragmentDayTaskListBinding

    private val taskListFragment = TaskListFragment()

    private var currentDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_day_task_list, container, false
        )
        val calendarCard: CardView = binding.calendarCard
        val calendarView: CalendarView = binding.calendarView

        val calendarBtn: Button = binding.calendarBtn
        val dateFormat = SimpleDateFormat.getDateInstance()
        calendarBtn.text = dateFormat.format(currentDate.time)
        calendarBtn.setOnClickListener {
            if (calendarCard.visibility == View.GONE) {
                calendarCard.visibility = View.VISIBLE
                calendarCard.animate()
                    .alpha(1.0f)
            } else {
                calendarCard.animate()
                    .alpha(0.0f)
                    .withEndAction { calendarCard.visibility = View.GONE }
            }
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            currentDate.set(year, month, dayOfMonth)
            calendarBtn.text = dateFormat.format(currentDate.time)
            calendarCard.animate()
                .alpha(0.0f)
                .withEndAction { calendarCard.visibility = View.GONE }
            updateChild()
        }

        binding.prevBtn.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            calendarView.date = currentDate.time.time
            calendarBtn.text = dateFormat.format(currentDate.time)
            updateChild()
        }

        binding.nextBtn.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            calendarView.date = currentDate.time.time
            calendarBtn.text = dateFormat.format(currentDate.time)
            updateChild()
        }

        binding.addBtn.setOnClickListener {
            view?.findNavController()?.navigate(
                R.id.action_global_newTaskFragment,
                bundleOf(
                    "title" to getString(R.string.new_task_fragment_title),
                    "task" to TaskWithCategories(Task("", Priority.Normal, currentDate.time), mutableListOf())
                )
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.child_content_container, taskListFragment)
            .commit()
        updateChild()
    }

    private fun updateChild() {
        val taskCategoryRepository = TaskCategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).taskCategoryDao())
        val taskListLiveData = taskCategoryRepository.getTaskList(currentDate.time)
        taskListLiveData.observe(this, Observer {
            taskListFragment.onTaskListAppeared(it)
        })
    }
}
