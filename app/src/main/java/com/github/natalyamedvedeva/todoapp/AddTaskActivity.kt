package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

const val ADDED_NAME_KEY = "ADDED_NAME_KEY"
const val ADDED_PRIORITY_KEY = "ADDED_PRIORITY_KEY"

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val nameEditText: EditText = findViewById(R.id.name_edit_text)
        val prioritySpinner: Spinner = findViewById(R.id.priority_spinner)
        initSpinner(prioritySpinner)
        val acceptButton: View = findViewById(R.id.accept_btn)
        acceptButton.setOnClickListener {
            val addedName = nameEditText.text.toString()
            val addedPriority = prioritySpinner.selectedItem as Priority
            val resultIntent = Intent()
            resultIntent.putExtra(ADDED_NAME_KEY, addedName)
            resultIntent.putExtra(ADDED_PRIORITY_KEY, addedPriority)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun initSpinner(spinner: Spinner) {
        val priorityArray = Priority.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(1)
    }
}