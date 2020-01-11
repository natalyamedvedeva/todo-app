package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.data.Priority
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.databinding.ActivityNewTaskBinding
import java.text.SimpleDateFormat
import java.util.*

const val ADDED_TASK_KEY = "ADDED_TASK_KEY"

class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding

    private var deadlineDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_task)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.new_task_activity_title)

        initPrioritySpinner(binding.prioritySpinner)
        initDeadlineTextView(binding.deadlineTextView)

        binding.autoTransferSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    applicationContext,
                    "The task will be rescheduled the next day",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.acceptBtn.setOnClickListener {
            val addedName = binding.nameEditText.text.toString()
            val addedPriority = binding.prioritySpinner.selectedItem as Priority
            val addedDescription = binding.descriptionEditText.text.toString()

            val task = Task(addedName, addedPriority, Date())
            task.deadline = deadlineDate?.time
            task.description = addedDescription
            task.autoTransfer = binding.autoTransferSwitch.isChecked

            val resultIntent = Intent()
            resultIntent.putExtra(ADDED_TASK_KEY, task)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
