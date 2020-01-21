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
import com.esafirm.imagepicker.model.Image
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.*
import com.github.natalyamedvedeva.todoapp.databinding.FragmentNewTaskBinding
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentNewTaskBinding

    private val imagesFragment = ImagesFragment()
    private val categoriesFragment = CategoriesFragment()

    private lateinit var imagesFragmentDataListener: OnImagesFragmentDataListener
    private lateinit var categoriesFragmentDataListener: OnCategoriesFragmentDataListener

    private lateinit var task :TaskWithCategories

    private var date: Calendar = Calendar.getInstance()
    private var deadlineDate: Calendar? = null

    private var images: ArrayList<Image> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_new_task, container, false)
        task = arguments?.getSerializable("task") as TaskWithCategories

        loadFragment()

        initPrioritySpinner(binding.prioritySpinner)
        initDateTextView(binding.dateTextView)
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

        categoriesFragment.editable = true

        val imagePicker = ImagePicker.create(this)
            .theme(R.style.AppTheme)
            .toolbarImageTitle("Tap to select")

        binding.imagesButton.setOnClickListener {
            imagePicker.limit(10)
                .origin(images)
                .start()
        }

        binding.acceptButton.setOnClickListener {
            val addedName = binding.nameEditText.text.toString()
            val addedPriority = binding.prioritySpinner.selectedItem as Priority
            val addedDescription = binding.descriptionEditText.text.toString()

            task.name = addedName
            task.priority = addedPriority
            task.date = date.time
            task.deadline = deadlineDate?.time
            task.description = addedDescription
            task.autoReschedule = binding.autoRescheduleSwitch.isChecked
            if (!images.isNullOrEmpty()) {
                task.images.clear()
                task.images.addAll(images.map { it.path })
            }
            task.categories.clear()
            task.categories.addAll(categoriesFragment.categoryList)

            val taskCategoryRepository = TaskCategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).taskCategoryDao())
            taskCategoryRepository.insertTask(task)

            view?.findNavController()?.popBackStack()
        }

        return binding.root
    }

    private fun loadFragment() {
        binding.nameEditText.setText(task.name)
        date.time = task.date

        task.deadline?.let {
            deadlineDate = Calendar.getInstance()
            deadlineDate!!.time = it
        }

        binding.autoRescheduleSwitch.isChecked = task.autoReschedule

        binding.descriptionEditText.setText(task.description)

        val oldImages = ArrayList<Image>()
        oldImages.addAll(task.images.map { Image(0, "", it) })
        images = oldImages

        // TODO: Categories
    }

    override fun onDestroyView() {
        super.onDestroyView()
        view?.clearFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images.clear()
            images.addAll(ImagePicker.getImages(data))
            updateImagesFragment()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.images_fragment_container, imagesFragment)
            .replace(R.id.categories_fragment_container, categoriesFragment)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is OnImagesFragmentDataListener) {
            imagesFragmentDataListener = childFragment
            updateImagesFragment()
        } else if (childFragment is OnCategoriesFragmentDataListener) {
            categoriesFragmentDataListener = childFragment
            updateCategoriesFragment()
        } /*else {
            throw RuntimeException("$childFragment must implements OnImagesFragmentDataListener or OnCategoriesFragmentDataListener")
        }*/
    }

    private fun updateImagesFragment() {
        imagesFragmentDataListener.onImagesAppeared(images.map { it.path })
    }

    private fun updateCategoriesFragment() {
        categoriesFragment.onCategoriesAppeared(task.categories)
    }

    private fun initPrioritySpinner(spinner: Spinner) {
        val priorityArray = Priority.values()
        val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, priorityArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(priorityArray.indexOf(task.priority))
    }

    private fun initDateTextView(textView: TextView) {
        val dateFormat = SimpleDateFormat.getDateInstance()
        val text = getString(R.string.date) + ": " + dateFormat.format(task.date)
        textView.text = text

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            date = GregorianCalendar(year, month, day)
            val str  = getString(R.string.date) + ": " + dateFormat.format(date.time)
            textView.text = str
        }

        binding.changeDateButton.setOnClickListener {
            val currentDate = date
            val datePickerDialog = DatePickerDialog(this.requireContext(), listener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }
    }

    private fun initDeadlineTextView(textView: TextView) {
        val dateFormat = SimpleDateFormat.getDateInstance()
        val noneText = getString(R.string.deadline) + ": " + getString(R.string.none)
        if (deadlineDate == null) {
            textView.text = noneText
        } else {
            val text = getString(R.string.date) + ": " + dateFormat.format(deadlineDate!!.time)
            textView.text = text
        }

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            deadlineDate = GregorianCalendar(year, month, day)
            val text = getString(R.string.deadline) + ": " + dateFormat.format(deadlineDate!!.time)
            textView.text = text
        }

        binding.setDeadlineButton.setOnClickListener {
            val currentDate = deadlineDate ?: Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext(), listener,
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.clearDeadlineButton.setOnClickListener {
            deadlineDate = null
            textView.text = noneText
        }
    }
}
