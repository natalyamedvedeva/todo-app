package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

const val ADDED_NAME_KEY = "ADDED_NAME_KEY"

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val nameEditText: EditText = findViewById(R.id.name_edit_text)
        val acceptButton: View = findViewById(R.id.accept_btn)
        acceptButton.setOnClickListener {
            val addedName = nameEditText.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra(ADDED_NAME_KEY, addedName)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}