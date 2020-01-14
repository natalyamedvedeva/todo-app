package com.github.natalyamedvedeva.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentDayTaskListBinding
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

class DayTaskListFragment : BaseFragment() {

    private lateinit var binding: FragmentDayTaskListBinding
    private lateinit var child: OnTaskListFragmentDataListener

    private var currentDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_day_task_list, container, false
        )
        val calendarCard: CardView = binding.calendarCard
        val calendarView: CalendarView = binding.calendarView

        val calendarBtn: Button = binding.calendarBtn
        val dateFormat = SimpleDateFormat.getDateInstance()
        calendarBtn.text = dateFormat.format(currentDate.time)
        calendarBtn.setOnClickListener {
            if (calendarCard.visibility == View.GONE) {
                calendarCard.visibility = View.VISIBLE
            } else {
                calendarCard.visibility = View.GONE
            }
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            currentDate.set(year, month, dayOfMonth)
            calendarBtn.text = dateFormat.format(currentDate.time)
            calendarCard.visibility = View.GONE
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
            val bundle = bundleOf("date" to currentDate.time)
            view?.findNavController()?.navigate(R.id.action_dayTaskListFragment_to_newTaskFragment, bundle)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childFragment = TaskListFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.child_content_container, childFragment)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is OnTaskListFragmentDataListener) {
            child = childFragment
            updateChild()
        } else {
            throw RuntimeException("$childFragment must implements OnTaskListFragmentDataListener")
        }
    }

    private fun updateChild() {
        //TODO simplify
        child.onTaskListAppeared(
            TaskRepository.getInstance(AppDatabase.getInstance(requireContext()).taskDao())
                .getTaskList(currentDate.time)
        )
    }
}
