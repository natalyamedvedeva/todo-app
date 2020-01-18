package com.github.natalyamedvedeva.todoapp.view

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskBinding
import java.text.SimpleDateFormat

class TaskFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskBinding

    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task, container, false)
        task = arguments?.getSerializable("task") as Task
        setHasOptionsMenu(true)

        binding.nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        if (task.deadline != null) {
            val dateFormat = SimpleDateFormat.getDateInstance()
            binding.deadlineTextView.text = dateFormat.format(task.deadline!!.time)
        }
        binding.descriptionTextView.text = task.description
        binding.autoRescheduleTextView.text = task.autoReschedule.toString()

        // Add the images to images layout
        task.images?.forEach {
            val image = ImageView(context)
            image.setImageURI(Uri.parse(it))
            image.layoutParams = LinearLayout.LayoutParams(600, 600)
            image.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.imagesLayout.addView(image)
        }

        return  binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_task) {
            val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(context!!).taskDao())
            taskRepository.delete(task)
            view?.findNavController()?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}