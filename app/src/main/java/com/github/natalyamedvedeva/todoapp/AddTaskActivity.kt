package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.data.Priority
import com.github.natalyamedvedeva.todoapp.databinding.ActivityAddTaskBinding
import java.text.SimpleDateFormat
import java.util.*

const val ADDED_NAME_KEY = "ADDED_NAME_KEY"
const val ADDED_PRIORITY_KEY = "ADDED_PRIORITY_KEY"
const val ADDED_DEADLINE_KEY = "ADDED_DEADLINE_KEY"
const val ADDED_DESCRIPTION_KEY = "ADDED_DESCRIPTION_KEY"

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    private var deadlineDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)

        initPrioritySpinner(binding.prioritySpinner)
        initDeadlineTextView(binding.deadlineTextView)

        binding.acceptBtn.setOnClickListener {
            val addedName = binding.nameEditText.text.toString()
            val addedPriority = binding.prioritySpinner.selectedItem as Priority
            val addedDescription = binding.descriptionEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra(ADDED_NAME_KEY, addedName)
            resultIntent.putExtra(ADDED_PRIORITY_KEY, addedPriority)
            resultIntent.putExtra(ADDED_DESCRIPTION_KEY, addedDescription)
            resultIntent.putExtra(ADDED_DEADLINE_KEY, deadlineDate?.time)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun initPrioritySpinner(spinner: Spinner) {
        val priorityArray = Priority.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityArray)
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
            val datePickerDialog = DatePickerDialog(this, listener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.clearDeadlineButton.setOnClickListener {
            deadlineDate = null
            textView.text = defaultText
        }
    }
}
