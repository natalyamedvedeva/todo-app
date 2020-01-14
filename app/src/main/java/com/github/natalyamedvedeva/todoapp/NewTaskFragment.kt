package com.github.natalyamedvedeva.todoapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Priority
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentNewTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class NewTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentNewTaskBinding

    private val date: Calendar = Calendar.getInstance()
    private var deadlineDate: Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false)
        date.time = arguments?.getSerializable("date") as Date

        initPrioritySpinner(binding.prioritySpinner)
        initDeadlineTextView(binding.deadlineTextView)

        binding.autoRescheduleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this.context,
                    getString(R.string.auto_reschedule_hint),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.acceptBtn.setOnClickListener {
            val addedName = binding.nameEditText.text.toString()
            val addedPriority = binding.prioritySpinner.selectedItem as Priority
            val addedDescription = binding.descriptionEditText.text.toString()

            val task = Task(addedName, addedPriority, date.time)
            task.deadline = deadlineDate?.time
            task.description = addedDescription
            task.autoReschedule = binding.autoRescheduleSwitch.isChecked

            val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(requireContext()).taskDao())
            taskRepository.insert(task)

            view?.clearFocus()
            fragmentManager?.popBackStack()
        }

        return binding.root
    }

    private fun initPrioritySpinner(spinner: Spinner) {
        val priorityArray = Priority.values()
        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, priorityArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(1)
    }

    private fun initDeadlineTextView(textView: TextView) {
        val defaultText = getString(R.string.deadline) + ": " +  getString(R.string.none)
        textView.text = defaultText

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            deadlineDate = GregorianCalendar(year, month, day)
            val dateFormat = SimpleDateFormat.getDateInstance()
            val text = getString(R.string.deadline) + ": " + dateFormat.format(deadlineDate!!.time)
            textView.text = text
        }

        binding.setDeadlineButton.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext(), listener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.clearDeadlineButton.setOnClickListener {
            deadlineDate = null
            textView.text = defaultText
        }
    }
}
