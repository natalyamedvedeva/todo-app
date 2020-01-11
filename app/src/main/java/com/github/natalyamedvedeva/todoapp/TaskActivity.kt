package com.github.natalyamedvedeva.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.databinding.ActivityTaskBinding
import java.text.SimpleDateFormat

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.task_activity_title)

        val task = intent.extras?.getSerializable("task") as Task
        binding.nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        if (task.deadline != null) {
            val dateFormat = SimpleDateFormat.getDateInstance()
            binding.deadlineTextView.text = dateFormat.format(task.deadline!!.time)
        }
        binding.descriptionTextView.text = task.description
        binding.autoTransferTextView.text = task.autoTransfer.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}