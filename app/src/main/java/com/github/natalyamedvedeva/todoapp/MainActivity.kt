package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager

private const val ADD_TASK_ACTIVITY_REQUEST_CODE = 1

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
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
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
            val addTaskActivityIntent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(addTaskActivityIntent, ADD_TASK_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            val addedName = data.getStringExtra(ADDED_NAME_KEY) ?: ""
            val addedPriority = data.getSerializableExtra(ADDED_PRIORITY_KEY) as Priority
            taskManager.addTask(currentDate.time, Task(addedName, addedPriority))
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
