package com.github.natalyamedvedeva.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.ActivityTaskBinding
import java.text.SimpleDateFormat


class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.task_activity_title)

        task = intent.extras?.getSerializable("task") as Task
        binding.nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        if (task.deadline != null) {
            val dateFormat = SimpleDateFormat.getDateInstance()
            binding.deadlineTextView.text = dateFormat.format(task.deadline!!.time)
        }
        binding.descriptionTextView.text = task.description
        binding.autoTransferTextView.text = task.autoReschedule.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_task) {
            val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(this).taskDao())
            taskRepository.delete(task)
            onBackPressed()
        }
        return true
    }
}