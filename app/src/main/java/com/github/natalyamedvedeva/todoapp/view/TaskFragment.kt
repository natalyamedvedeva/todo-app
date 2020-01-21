package com.github.natalyamedvedeva.todoapp.view

import android.graphics.Paint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.TaskWithCategories
import com.github.natalyamedvedeva.todoapp.databinding.FragmentTaskBinding
import java.text.SimpleDateFormat

class TaskFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskBinding

    private val imagesFragment = ImagesFragment()
    private val categoriesFragment = CategoriesFragment()

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

        if (!task.description.isNullOrEmpty()) {
            binding.descriptionTextView.text = task.description
        } else {
            binding.descriptionTextView.visibility = View.GONE
        }

        if (task.images.isEmpty()) {
            binding.imagesFragmentContainer.visibility = View.GONE
        }

        if (task.categories.isEmpty()) {
            binding.categoriesLabelTextView.visibility = View.GONE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.images_fragment_container, imagesFragment)
            .replace(R.id.categories_fragment_container, categoriesFragment)
            .commit()
        updateImagesFragment()
        updateCategoriesFragment()
    }

    private fun updateImagesFragment() {
        if (::task.isInitialized && task.images.isNotEmpty()) {
            imagesFragment.onImagesAppeared(task.images)
        }
    }

    private fun updateCategoriesFragment() {
        if (::task.isInitialized && task.categories.isNotEmpty()) {
            categoriesFragment.onCategoriesAppeared(task.categories)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
        menu.findItem(R.id.done_task).isVisible = !task.done
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuController = TaskMenuController(view!!, task)
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