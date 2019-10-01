package com.github.natalyamedvedeva.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val tasks = mutableListOf("Task1", "Task2", "Task3", "Task4", "Task5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.list_view)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks)
        listView.adapter = adapter
        // Create add button
        var addBtn = Button(this)
        addBtn.setText(R.string.add)
        listView.addFooterView(addBtn)
        addBtn.setOnClickListener {
            tasks.add("Task" + (tasks.size + 1))
            adapter.notifyDataSetChanged()
        }
    }
}
