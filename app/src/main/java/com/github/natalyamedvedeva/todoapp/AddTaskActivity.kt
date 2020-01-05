package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

const val ADDED_NAME_KEY = "ADDED_NAME_KEY"
const val ADDED_PRIORITY_KEY = "ADDED_PRIORITY_KEY"
const val ADDED_DEADLINE_KEY = "ADDED_DEADLINE_KEY"
const val ADDED_DESCRIPTION_KEY = "ADDED_DESCRIPTION_KEY"

class AddTaskActivity : AppCompatActivity() {

    private var deadlineDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val nameEditText: EditText = findViewById(R.id.name_edit_text)
        val prioritySpinner: Spinner = findViewById(R.id.priority_spinner)
        val deadlineEditText: TextView = findViewById(R.id.deadline_text_view)
        val descriptionEditText: EditText = findViewById(R.id.description_edit_text)
        initPrioritySpinner(prioritySpinner)
        initDeadlineEditText(deadlineEditText)

        val acceptButton: View = findViewById(R.id.accept_btn)
        acceptButton.setOnClickListener {
            val addedName = nameEditText.text.toString()
            val addedPriority = prioritySpinner.selectedItem as Priority
            val addedDescription = descriptionEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra(ADDED_NAME_KEY, addedName)
            resultIntent.putExtra(ADDED_PRIORITY_KEY, addedPriority)
            resultIntent.putExtra(ADDED_DESCRIPTION_KEY, addedDescription)
            resultIntent.putExtra(ADDED_DEADLINE_KEY, deadlineDate)
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

    private fun initDeadlineEditText(textView: TextView) {
        val defaultText = getString(R.string.deadline) + ": " +  getString(R.string.none)
        textView.text = defaultText

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            deadlineDate = GregorianCalendar(year, month, day)
            val dateFormat = SimpleDateFormat.getDateInstance()
            val text = getString(R.string.deadline) + ": " + dateFormat.format(deadlineDate!!.time)
            textView.text = text
        }

        findViewById<Button>(R.id.set_deadline_button).setOnClickListener {
            val currentDate = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this, listener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        findViewById<Button>(R.id.clear_deadline_button).setOnClickListener {
            deadlineDate = null
            textView.text = defaultText
        }
    }
}