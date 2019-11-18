package com.github.natalyamedvedeva.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var taskItemAdapter: TaskItemAdapter

    private var currentDate = Calendar.getInstance()
    private var taskManager = TaskManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarCard: CardView = findViewById(R.id.calendar_card)

        val calendarBtn: Button = findViewById(R.id.calendar_btn)
        val dateFormat = SimpleDateFormat.getDateInstance()
        calendarBtn.text = dateFormat.format(currentDate.time)
        calendarBtn.setOnClickListener {
            if (calendarCard.visibility == View.GONE) {
                calendarCard.visibility = View.VISIBLE
            } else {
                calendarCard.visibility = View.GONE
            }
        }

        val calendarView: CalendarView = findViewById(R.id.calendar_view)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            currentDate.set(year, month, dayOfMonth)
            calendarBtn.text = dateFormat.format(currentDate.time)
            taskListChanged()
        }

        val prevBtn: ImageButton = findViewById(R.id.prev_btn)
        prevBtn.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            calendarBtn.text = dateFormat.format(currentDate.time)
            taskListChanged()
        }

        val nextBtn: ImageButton = findViewById(R.id.next_btn)
        nextBtn.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            calendarBtn.text = dateFormat.format(currentDate.time)
            taskListChanged()
        }

        initRecyclerView()

        val addBtn: View = findViewById(R.id.add_btn)
        addBtn.setOnClickListener {
            taskManager.addTask(currentDate.time, Task("Task"))
            taskListChanged()
        }
    }

    private fun initRecyclerView() {
        tasksRecyclerView = findViewById(R.id.tasks_recycler_view)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        taskItemAdapter = TaskItemAdapter()
        taskItemAdapter.addItems(taskManager.getTaskList(currentDate.time))
        tasksRecyclerView.adapter = taskItemAdapter
    }

    private fun taskListChanged() {
        taskItemAdapter.clearItems()
        taskItemAdapter.addItems(taskManager.getTaskList(currentDate.time))
    }
}
