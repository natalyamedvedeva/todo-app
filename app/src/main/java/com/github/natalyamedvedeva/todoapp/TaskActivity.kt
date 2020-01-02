package com.github.natalyamedvedeva.todoapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val task = intent.extras?.getSerializable("task") as Task
        val nameTextView: TextView = findViewById(R.id.name_text_view)
        val descriptionTextView: TextView = findViewById(R.id.description_text_view)
        nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        descriptionTextView.text = task.description
    }
}