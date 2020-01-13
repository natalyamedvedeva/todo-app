package com.github.natalyamedvedeva.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.ActivityMainBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider
import java.util.Calendar

private const val ADD_TASK_ACTIVITY_REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var taskItemAdapter: TaskItemAdapter

    private var currentDate = Calendar.getInstance()

    private var taskRepository: TaskRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        EmojiManager.install(IosEmojiProvider())
        super.onCreate(savedInstanceState)
        taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(applicationContext).taskDao())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
        toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }
        supportActionBar?.title = getString(R.string.main_activity_title)

        val calendarCard: CardView = binding.calendarCard
        val calendarView: CalendarView = binding.calendarView

        val calendarBtn: Button = binding.calendarBtn
        val dateFormat = SimpleDateFormat.getDateInstance()
        calendarBtn.text = dateFormat.format(currentDate.time)
        calendarBtn.setOnClickListener {
            if (calendarCard.visibility == View.GONE) {
                calendarCard.visibility = View.VISIBLE
            } else {
                calendarCard.visibility = View.GONE
            }
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            currentDate.set(year, month, dayOfMonth)
            calendarBtn.text = dateFormat.format(currentDate.time)
            calendarCard.visibility = View.GONE
        }

        binding.prevBtn.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            calendarView.date = currentDate.time.time
            calendarBtn.text = dateFormat.format(currentDate.time)
        }

        binding.nextBtn.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            calendarView.date = currentDate.time.time
            calendarBtn.text = dateFormat.format(currentDate.time)
        }

        initRecyclerView()

        binding.addBtn.setOnClickListener {
            val addTaskActivityIntent = Intent(this, NewTaskActivity::class.java)
            startActivityForResult(addTaskActivityIntent, ADD_TASK_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val task = data.getSerializableExtra(ADDED_TASK_KEY) as Task
            task.date = currentDate.time
            taskRepository!!.insert(task)
        }
    }

    private fun initRecyclerView() {
        tasksRecyclerView = binding.tasksRecyclerView
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        taskItemAdapter = TaskItemAdapter()
        val taskListLiveData = taskRepository!!.getTaskList(currentDate.time)
        taskListLiveData.observe(this, Observer {
            taskItemAdapter.clearItems()
            taskItemAdapter.addItems(it)
        })
        tasksRecyclerView.adapter = taskItemAdapter
    }
}
