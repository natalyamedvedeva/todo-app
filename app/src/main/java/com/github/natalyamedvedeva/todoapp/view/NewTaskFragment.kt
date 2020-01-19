package com.github.natalyamedvedeva.todoapp.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.esafirm.imagepicker.features.ImagePicker
import com.facebook.drawee.view.SimpleDraweeView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Priority
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentNewTaskBinding
import com.github.natalyamedvedeva.todoapp.utils.getImagePath
import com.github.natalyamedvedeva.todoapp.utils.saveImage
import com.stfalcon.frescoimageviewer.ImageViewer
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

class NewTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var child: OnImagesFragmentDataListener

    private val date: Calendar = Calendar.getInstance()
    private var deadlineDate: Calendar? = null

    private val maxImagesCount = 10
    private var imagesCount = 0
    private var images: MutableList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_new_task, container, false)
        date.time = arguments?.getSerializable("date") as Date

        initPrioritySpinner(binding.prioritySpinner)
        initDeadlineTextView(binding.deadlineTextView)

        binding.autoRescheduleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this.context,
                    getString(R.string.auto_reschedule_hint),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val imagePicker = ImagePicker.create(this)
            .theme(R.style.AppTheme)
            .toolbarImageTitle("Tap to select")

        binding.imagesButton.setOnClickListener {
            imagePicker
                .limit(maxImagesCount - imagesCount)
                .start()
        }

        binding.acceptButton.setOnClickListener {
            val addedName = binding.nameEditText.text.toString()
            val addedPriority = binding.prioritySpinner.selectedItem as Priority
            val addedDescription = binding.descriptionEditText.text.toString()

            val task = Task(addedName, addedPriority, date.time)
            task.deadline = deadlineDate?.time
            task.description = addedDescription
            task.autoReschedule = binding.autoRescheduleSwitch.isChecked

            val uuidList = mutableListOf<String>()
            images?.forEach { uuidList.add(saveImage(context!!, it)) }
            task.images = uuidList

            val taskRepository = TaskRepository.getInstance(AppDatabase.getInstance(requireContext()).taskDao())
            taskRepository.insert(task)

            view?.clearFocus()
            view?.findNavController()?.popBackStack()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            if (images == null) {
                images = mutableListOf()
            }
            val result = ImagePicker.getImages(data)
            imagesCount += result.size
            result.forEach { images?.add(it.path) }
            updateChild()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childFragment = ImagesFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.child_fragment_container, childFragment)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is OnImagesFragmentDataListener) {
            child = childFragment
            updateChild()
        } else {
            throw RuntimeException("$childFragment must implements OnImagesFragmentDataListener")
        }
    }

    private fun updateChild() {
        if (images != null) {
            child.onImagesAppeared(images!!)
        }
    }

    private fun initPrioritySpinner(spinner: Spinner) {
        val priorityArray = Priority.values()
        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, priorityArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(1)
    }

    private fun initDeadlineTextView(textView: TextView) {
        val defaultText = getString(R.string.deadline) + ": " +  getString(
            R.string.none
        )
        textView.text = defaultText

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            deadlineDate = GregorianCalendar(year, month, day)
            val dateFormat = SimpleDateFormat.getDateInstance()
            val text = getString(R.string.deadline) + ": " + dateFormat.format(deadlineDate!!.time)
            textView.text = text
        }

        binding.setDeadlineButton.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext(), listener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.clearDeadlineButton.setOnClickListener {
            deadlineDate = null
            textView.text = defaultText
        }
    }
}
