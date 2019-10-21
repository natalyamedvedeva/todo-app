package com.github.natalyamedvedeva.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var currentDay = Calendar.getInstance()
    private lateinit var listView: ListView
    private val tasks = mutableListOf("Task1", "Task2", "Task3", "Task4", "Task5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarCard: CardView = findViewById(R.id.calendar_card)

        val calendarBtn: Button = findViewById(R.id.calendar_btn)
        val dateFormat = SimpleDateFormat.getDateInstance()
        calendarBtn.text = dateFormat.format(currentDay.time)
        calendarBtn.setOnClickListener {
            if (calendarCard.visibility == View.GONE) {
                calendarCard.visibility = View.VISIBLE
            } else {
                calendarCard.visibility = View.GONE
            }
        }

        val prevBtn: ImageButton = findViewById(R.id.prev_btn)
        prevBtn.setOnClickListener {
            currentDay.add(Calendar.DAY_OF_MONTH, -1)
            calendarBtn.text = dateFormat.format(currentDay.time)
        }

        val nextBtn: ImageButton = findViewById(R.id.next_btn)
        nextBtn.setOnClickListener {
            currentDay.add(Calendar.DAY_OF_MONTH, 1)
            calendarBtn.text = dateFormat.format(currentDay.time)
        }

        listView = findViewById(R.id.list_view)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks)
        listView.adapter = adapter

        val addBtn: View = findViewById(R.id.add_btn)
        addBtn.setOnClickListener {
            tasks.add("Task" + (tasks.size + 1))
            adapter.notifyDataSetChanged()
        }
    }
}
