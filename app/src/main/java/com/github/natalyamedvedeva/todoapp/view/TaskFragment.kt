package com.github.natalyamedvedeva.todoapp.view

import android.graphics.Paint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.TaskWithCategories
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskBinding
import java.lang.RuntimeException
import java.text.SimpleDateFormat

class TaskFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskBinding

    private val imagesFragment = ImagesFragment()

    private lateinit var imagesFragmentDataListener: OnImagesFragmentDataListener

    private lateinit var task: TaskWithCategories

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task, container, false)
        task = arguments?.getSerializable("task") as TaskWithCategories
        setHasOptionsMenu(true)

        binding.nameTextView.text = task.name
        var icon = ""
        if (task.done) {
            binding.nameTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            if (task.task.isDeadlineClose()) {
                icon = " 🔥"
            }
        }
        val nameText = task.name + icon
        binding.nameTextView.text = nameText

        val priorityText = getString(R.string.priority) + ": " + task.priority.name
        binding.priorityTextView.text = priorityText

        val dateFormat = SimpleDateFormat.getDateInstance()
        val dateText = getString(R.string.date) + ": " + dateFormat.format(task.date)
        binding.dateTextView.text = dateText

        if (task.deadline != null) {
            val deadlineText = getString(R.string.deadline) + ": " + dateFormat.format(task.deadline!!)
            binding.deadlineTextView.text = deadlineText
        } else {
            binding.deadlineTextView.visibility = View.GONE
        }

        if (task.autoReschedule) {
            val autoRescheduleText = getString(R.string.auto_reschedule) + " is enabled"
            binding.autoRescheduleTextView.text = autoRescheduleText
        } else {
            binding.autoRescheduleTextView.visibility = View.GONE
        }

        binding.descriptionTextView.text = task.description

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.images_fragment_container, imagesFragment)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is OnImagesFragmentDataListener) {
            imagesFragmentDataListener = childFragment
            updateImagesFragment()
        } else {
            throw RuntimeException("$childFragment must implements OnImagesFragmentDataListener")
        }
    }

    private fun updateImagesFragment() {
        if (::task.isInitialized && !task.images.isNullOrEmpty()) {
            imagesFragmentDataListener.onImagesAppeared(task.images)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
        menu.findItem(R.id.done_task).isVisible = !task.done
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuController = TaskMenuController(view!!, task.task)
        if (item.itemId == R.id.done_task) {
            menuController.done()
            view?.findNavController()?.popBackStack()
        }
        if (item.itemId == R.id.edit_task) {
            menuController.edit()
        }
        if (item.itemId == R.id.delete_task) {
            menuController.delete()
            view?.findNavController()?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}