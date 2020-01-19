package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.facebook.drawee.view.SimpleDraweeView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskBinding
import com.github.natalyamedvedeva.todoapp.utils.getImagePath
import com.stfalcon.frescoimageviewer.ImageViewer
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

        // Read the images from the internal storage and add to the images layout
        task.images?.forEachIndexed { index, image ->
            val view = SimpleDraweeView(context)
            view.layoutParams = LinearLayout.LayoutParams(600, 600)
            view.setImageURI("file://" + getImagePath(context!!, image))
            binding.imagesLayout.addView(view)

            val uris = mutableListOf<String>()
            task.images?.forEach { uuid -> uris.add("file://" + getImagePath(context!!, uuid)) }
            view.setOnClickListener {
                ImageViewer.Builder(context, uris)
                    .setStartPosition(index)
                    .show()
            }
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