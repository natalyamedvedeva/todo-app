package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
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

        binding.nameTextView.text = String.format("%s - %s", task.name, task.priority.name)
        if (task.deadline != null) {
            val dateFormat = SimpleDateFormat.getDateInstance()
            binding.deadlineTextView.text = dateFormat.format(task.deadline!!.time)
        }
        binding.descriptionTextView.text = task.description
        binding.autoRescheduleTextView.text = task.autoReschedule.toString()

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
            updateChild()
        } else {
            throw RuntimeException("$childFragment must implements OnImagesFragmentDataListener")
        }
    }

    private fun updateChild() {
        if (::task.isInitialized && !task.images.isNullOrEmpty()) {
            imagesFragmentDataListener.onImagesAppeared(task.images)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_task) {
            val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(context!!).taskDao())
            taskRepository.delete(task.task)
            view?.findNavController()?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}